//
//  LiveRoomViewController.swift
//  OpenLive
//
//  Created by GongYuhua on 6/25/16.
//  Copyright © 2016 Agora. All rights reserved.
//

import UIKit
import AgoraRtcKit

protocol LiveVCDataSource: NSObjectProtocol {
    func liveVCNeedAgoraKit() -> AgoraRtcEngineKit
    func liveVCNeedSettings() -> Settings
}

class LiveRoomViewController: UIViewController, UITextFieldDelegate {
    
    @IBOutlet weak var broadcastersView: AGEVideoContainer!
    @IBOutlet weak var placeholderView: UIImageView!
    
    @IBOutlet weak var videoMuteButton: UIButton!
    @IBOutlet weak var audioMuteButton: UIButton!
    @IBOutlet weak var beautyEffectButton: UIButton!
    
    @IBOutlet var sessionButtons: [UIButton]!
    
    @IBOutlet weak var searchButton: UIButton!
    @IBOutlet weak var saysomethingText: UITextField!
    @IBOutlet weak var searchText: UITextField!
    @IBOutlet weak var searchPosition: NSLayoutConstraint!
    @IBOutlet weak var saysomethingPosition: NSLayoutConstraint!
    @IBOutlet weak var clapButton: UIButton!
    @IBOutlet weak var recButton: UIButton!
    @IBOutlet weak var addPersonButton: UIButton!
    
    private let beautyOptions: AgoraBeautyOptions = {
        let options = AgoraBeautyOptions()
        options.lighteningContrastLevel = .normal
        options.lighteningLevel = 0.7
        options.smoothnessLevel = 0.5
        options.rednessLevel = 0.1
        return options
    }()
    
    private var agoraKit: AgoraRtcEngineKit {
        return dataSource!.liveVCNeedAgoraKit()
    }
    
    private var settings: Settings {
        return dataSource!.liveVCNeedSettings()
    }
    
    private var isMutedVideo = false {
        didSet {
            // mute local video
            agoraKit.muteLocalVideoStream(isMutedVideo)
            videoMuteButton.isSelected = isMutedVideo
        }
    }
    
    private var isMutedAudio = false {
        didSet {
            // mute local audio
            agoraKit.muteLocalAudioStream(isMutedAudio)
            audioMuteButton.isSelected = isMutedAudio
        }
    }
    
    private var isBeautyOn = false {
        didSet {
            // improve local render view
            agoraKit.setBeautyEffectOptions(isBeautyOn,
                                            options: isBeautyOn ? beautyOptions : nil)
            beautyEffectButton.isSelected = isBeautyOn
        }
    }
    
    private var isSwitchCamera = false {
        didSet {
            agoraKit.switchCamera()
        }
    }
    
    private var videoSessions = [VideoSession]() {
        didSet {
            placeholderView.isHidden = (videoSessions.count == 0 ? false : true)
            // update render view layout
            updateBroadcastersView()
        }
    }
    
    private let maxVideoSession = 4
    
    private var isShowSearch = false
    
    private var originY: CGFloat = 0
    
    private var heartSize: CGFloat = 36
    
    private var isRecording: Bool = false
    
    weak var dataSource: LiveVCDataSource?
    
    private var editingText: UITextField?
    
    private var label: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        searchText.isHidden = true
        searchText.delegate = self
        saysomethingText.delegate = self
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillAppear), name: UIResponder.keyboardWillShowNotification, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillDisappear), name: UIResponder.keyboardWillHideNotification, object: nil)
        updateButtonsVisiablity()
        loadAgoraKit()
    }
    
    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .lightContent
    }
    
    //MARK: - ui action
    @IBAction func doSwitchCameraPressed(_ sender: UIButton) {
        isSwitchCamera.toggle()
    }
    
    @IBAction func doBeautyPressed(_ sender: UIButton) {
        isBeautyOn.toggle()
    }
    
    @IBAction func doMuteVideoPressed(_ sender: UIButton) {
        isMutedVideo.toggle()
    }
    
    @IBAction func doMuteAudioPressed(_ sender: UIButton) {
        isMutedAudio.toggle()
    }
    
    @IBAction func doLeavePressed(_ sender: UIButton) {
        leaveChannel()
    }
    
    @IBAction func doSearchPressed(_ sender: UIButton) {
        let currentSearchShowStatus = searchText.isHidden
        searchText.isHidden = !currentSearchShowStatus
        searchText.resignFirstResponder()
    }
    
    @IBAction func doClapPressed(_ sender: Any) {
        self.showMyLove()
    }
    @IBAction func doRecPressed(_ sender: Any) {
        if !isRecording {
            recButton.setImage(UIImage(systemName: "stop.circle"), for: .normal)
            recButton.tintColor = .red
            self.isRecording = true
            self.getRecAuth()
        } else {
            recButton.setImage(UIImage(systemName: "play.circle"), for: .normal)
            recButton.tintColor = .white
            self.isRecording = false
            let alert = UIAlertController(title: "温馨小提示", message: "录制停止，录像已保存到云端处理", preferredStyle: .alert)
            let ok = UIAlertAction(title: "OK", style: .default, handler: nil)
            
            alert.addAction(ok)
            self.present(alert, animated: true, completion: nil)
        }
    }
    @IBAction func doAddPeoplePressed(_ sender: Any) {
        let alert = UIAlertController(title: "分享拉人", message: "请选择拉人方式", preferredStyle: .actionSheet)
        let cancel = UIAlertAction(title: "取消", style: .cancel, handler: nil)
        let del = UIAlertAction(title: "微信拉人", style: .default, handler: {
            ACTION in
            let wxAlert = UIAlertController(title: "温馨小提示", message: "微信不见了", preferredStyle: .alert)
            let wxOk = UIAlertAction(title: "OK", style: .default, handler: nil)
            wxAlert.addAction(wxOk)
            self.present(wxAlert, animated: true, completion: nil)
        })
        let ok = UIAlertAction(title: "短信拉人", style: .default, handler: {
            ACTION in
            let dxAlert = UIAlertController(title: "提示", message: "请输入手机号码", preferredStyle: .alert)
            
            dxAlert.addTextField{(usernameText) ->Void in
                usernameText.placeholder = "手机号码"
            }
            
            let dxCancel = UIAlertAction(title: "取消", style: .cancel, handler: nil)
            let dxLogin = UIAlertAction(title: "发送", style: .default, handler: {
                ACTION in
                print(dxAlert.textFields?.count ?? -1)
                let text1 = dxAlert.textFields?.first?.text
                print(text1!)
                
                let sAlert = UIAlertController(title: "温馨小提示", message: "咻~发送给\(text1!)失败啦", preferredStyle: .alert)
                let sOk = UIAlertAction(title: "OK", style: .cancel, handler: nil)
                sAlert.addAction(sOk)
                self.present(sAlert, animated: true, completion: nil)
            })
            
            dxAlert.addAction(dxCancel)
            dxAlert.addAction(dxLogin)
            self.present(dxAlert, animated: true, completion: nil)
        })
        alert.addAction(del)
        alert.addAction(ok)
        alert.addAction(cancel)
        self.present(alert, animated: true, completion: nil)
    }
    
    
    //键盘弹起
    @objc func keyboardWillAppear(notification: NSNotification) {
        // 获得软键盘的高
        let keyboardinfo = notification.userInfo![UIResponder.keyboardFrameBeginUserInfoKey]
        let keyboardheight:CGFloat = (keyboardinfo as AnyObject).cgRectValue.size.height
       
        //计算输入框和软键盘的高度差
        self.originY = self.editingText!.center.y
        let rect = self.editingText!.convert(self.editingText!.bounds, to: self.view)
        let y = self.view.bounds.height - rect.origin.y - self.editingText!.bounds.height - keyboardheight
        print(keyboardheight)
        print(self.originY)
        self.view.layoutIfNeeded()
        if self.editingText! == self.searchText {
            self.searchPosition.constant = keyboardheight - 36 >= 240 ? keyboardheight - 36 : 250
        } else {
            self.saysomethingPosition.constant = keyboardheight
        }
        
        print(self.searchPosition.constant)
        print(self.saysomethingPosition.constant)
            
        //设置中心点偏移
        UIView.animate(withDuration: 0.5) {
            if y < 0 {
                self.view.layoutIfNeeded()
                //self.editingText!.center.y = (self.originY + y)
                //NSLayoutConstraint.activate([NSY])
            }
        }
    }
    
    //键盘落下
    @objc func keyboardWillDisappear(notification:NSNotification){
        //软键盘收起的时候恢复原始偏移
        self.view.layoutIfNeeded()
        self.searchPosition.constant = 15
        self.saysomethingPosition.constant = 0
        UIView.animate(withDuration: 0.5) {
            self.view.layoutIfNeeded()
        }
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
        self.searchText.resignFirstResponder()
        self.saysomethingText.resignFirstResponder()
    }
    
    func showMyLove() {
        let heart = HeartView(frame: CGRect(x: 0, y: 0, width: heartSize, height: heartSize))
        view.addSubview(heart)
        let fountainSource = CGPoint(x: clapButton.center.x, y: clapButton.center.y)
        heart.center = fountainSource
        heart.animateInView(view: self.view)
    }
    
    func sendDM(text: String!){
        //主屏幕的宽高
        let WIDTH:CGFloat = self.view.frame.size.width;
        //减50 是减去Lable本身的高度
        let HEIGHT:CGFloat = self.view.frame.size.height - 50 - 300;
        // random: 在height中随机取一个数值
        let random:CGFloat = CGFloat(arc4random_uniform(UInt32(HEIGHT)))
        label = UILabel(frame: CGRect(x: WIDTH,y:random, width: 400, height: 50))
        self.view.addSubview(label)
        
        //判断文本输入框的内容是否为空
        if (text.isEmpty) {
            print("输入的文本不能为空")
        } else {
            //获取输入的内容
            let getText:String = text
            //调用分类(分类很简单的,就是设置三原色)
            //label.textColor = UIColor(red: CGFloat(arc4random_uniform(UInt32(255)))/255, green: CGFloat(arc4random_uniform(UInt32(255)))/255, blue: CGFloat(arc4random_uniform(UInt32(255)))/255, alpha: 1)
            label.textColor = .white
            label.shadowColor = .gray
            label.shadowOffset = CGSize(width: 0.1, height: 1)
            //设置Lable的字体大小
            label.font = UIFont.systemFont(ofSize: 16)
            //将内容赋值给lable
            label.text = getText
            let text:String = self.label.text!//获取label的text
            let attributes = [NSAttributedString.Key.font: self.label.font!]//计算label的字体
            label.frame = labelSize(text: text, attributes: attributes as [NSObject : AnyObject])//调用计算label宽高的方法
            label.frame = CGRect(x: WIDTH, y: random, width: label.frame.width, height: 50)
            //移动文字
            moveText(label)
        }
        //textField.text = nil
    }
    
    func moveText(_ label: UILabel) {
        //视图动画效果
        UIView.animate(withDuration: 10, animations: {
            //单独这一条语句无效果
            /*
             弹幕的原理是将Lable从最右侧移至最左侧,改变的是X的值,其他的值都不改变,所以从新设置Lable的X值就行了
             */
            label.frame = CGRect(x: 0 - label.frame.size.width, y: label.frame.origin.y, width: label.frame.size.width, height: label.frame.size.height)
        }) { (Bool) in
            //移动结束之后,从父视图中移除lable
            label.removeFromSuperview()
        }
    }
    
    /**
    计算label的宽度和高度

    :param: text       label的text的值
    :param: attributes label设置的字体

    :returns: 返回计算后label的CGRecet
    */
    func labelSize(text:String ,attributes : [NSObject : AnyObject]) -> CGRect{
        var size = CGRect();
        let size2 = CGSize(width: 1000, height: 0);//设置label的最大宽度
        size = text.boundingRect(with: size2, options: NSStringDrawingOptions.usesLineFragmentOrigin, attributes: (attributes as! [NSAttributedString.Key : Any]) , context: nil);
        print(size)
        return size
    }
    
    func getRecAuth() {
        // 填入 Customer ID 和 Customer Certificate，计算 loginString。
        let username = "1023d634a90d455ab30579d6a97fa3d2"
        let password = "730b225f75494ec19df2263ae3096553"
        let loginString = String(format: "%@:%@", username, password)
        // 填入 loginString，计算 loginData。
        let loginData = loginString.data(using: String.Encoding.utf8)!
        // 填入 loginData（使用 Base64 算法编码的 LoginData），计算 base64LoginString，即你要的 Authorization 字段。
        let base64LoginString = loginData.base64EncodedString()
        
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        self.view.endEditing(true)
        textField.resignFirstResponder()
        print(textField.text!)
        if textField == self.searchText && textField.text != "" {
            let urlString: String! = "https://www.google.com/search?q=\(textField.text!.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed) ?? "")"
            let url:URL! = URL.init(string: urlString)!
            UIApplication.shared.open(url!, options: [:], completionHandler: nil)
            textField.text = ""
            print(textField.center)
        } else if textField == self.saysomethingText && textField.text != "" {
            // do something
            self.sendDM(text: textField.text)
            textField.text = ""
            self.sendDM(text: "你发送了一条弹幕耶")
        }
        
        return true
    }
    
    //获得正在输入的UITextField
    func textFieldDidBeginEditing(_ textField: UITextField) {
        editingText = textField
    }
    
}

private extension LiveRoomViewController {
    func updateBroadcastersView() {
        // video views layout
        if videoSessions.count == maxVideoSession {
            broadcastersView.reload(level: 0, animated: true)
        } else {
            var rank: Int
            var row: Int
            
            if videoSessions.count == 0 {
                return
            } else if videoSessions.count == 1 {
                rank = 1
                row = 1
            } else if videoSessions.count == 2 {
                rank = 1
                row = 2
            } else {
                rank = 2
                row = Int(ceil(Double(videoSessions.count) / Double(rank)))
            }
            
            let itemWidth = CGFloat(1.0) / CGFloat(rank)
            let itemHeight = CGFloat(1.0) / CGFloat(row)
            let itemSize = CGSize(width: itemWidth, height: itemHeight)
            let layout = AGEVideoLayout(level: 0)
                        .itemSize(.scale(itemSize))
            
            broadcastersView
                .listCount { [unowned self] (_) -> Int in
                    return self.videoSessions.count
                }.listItem { [unowned self] (index) -> UIView in
                    return self.videoSessions[index.item].hostingView
                }
            
            broadcastersView.setLayouts([layout], animated: true)
        }
    }
    
    func updateButtonsVisiablity() {
        guard let sessionButtons = sessionButtons else {
            return
        }
        
        let isHidden = settings.role == .audience
        
        for item in sessionButtons {
            item.isHidden = isHidden
        }
    }
    
    func setIdleTimerActive(_ active: Bool) {
        UIApplication.shared.isIdleTimerDisabled = !active
    }
}

private extension LiveRoomViewController {
    func getSession(of uid: UInt) -> VideoSession? {
        for session in videoSessions {
            if session.uid == uid {
                return session
            }
        }
        return nil
    }
    
    func videoSession(of uid: UInt) -> VideoSession {
        if let fetchedSession = getSession(of: uid) {
            return fetchedSession
        } else {
            let newSession = VideoSession(uid: uid)
            videoSessions.append(newSession)
            return newSession
        }
    }
}

//MARK: - Agora Media SDK
private extension LiveRoomViewController {
    func loadAgoraKit() {
        guard let channelId = settings.roomName else {
            return
        }
        
        setIdleTimerActive(false)
        
        // Step 1, set delegate to inform the app on AgoraRtcEngineKit events
        agoraKit.delegate = self
        // Step 2, set live broadcasting mode
        // for details: https://docs.agora.io/cn/Video/API%20Reference/oc/Classes/AgoraRtcEngineKit.html#//api/name/setChannelProfile:
        agoraKit.setChannelProfile(.liveBroadcasting)
        // set client role
        agoraKit.setClientRole(settings.role)
        
        // Step 3, Warning: only enable dual stream mode if there will be more than one broadcaster in the channel
        agoraKit.enableDualStreamMode(true)
        
        // Step 4, enable the video module
        agoraKit.enableVideo()
        // set video configuration
        agoraKit.setVideoEncoderConfiguration(
            AgoraVideoEncoderConfiguration(
                size: settings.dimension,
                frameRate: settings.frameRate,
                bitrate: AgoraVideoBitrateStandard,
                orientationMode: .adaptative
            )
        )
        
        // if current role is broadcaster, add local render view and start preview
        if settings.role == .broadcaster {
            addLocalSession()
            agoraKit.startPreview()
        }
        
        // Step 5, join channel and start group chat
        // If join  channel success, agoraKit triggers it's delegate function
        // 'rtcEngine(_ engine: AgoraRtcEngineKit, didJoinChannel channel: String, withUid uid: UInt, elapsed: Int)'
        agoraKit.joinChannel(byToken: KeyCenter.Token, channelId: channelId, info: nil, uid: 0, joinSuccess: nil)
        
        // Step 6, set speaker audio route
        agoraKit.setEnableSpeakerphone(true)
    }
    
    func addLocalSession() {
        let localSession = VideoSession.localSession()
        localSession.updateInfo(fps: settings.frameRate.rawValue)
        videoSessions.append(localSession)
        agoraKit.setupLocalVideo(localSession.canvas)
    }
    
    func leaveChannel() {
        // Step 1, release local AgoraRtcVideoCanvas instance
        agoraKit.setupLocalVideo(nil)
        // Step 2, leave channel and end group chat
        agoraKit.leaveChannel(nil)
        
        // Step 3, if current role is broadcaster,  stop preview after leave channel
        if settings.role == .broadcaster {
            agoraKit.stopPreview()
        }
        
        setIdleTimerActive(true)
        
        navigationController?.popViewController(animated: true)
    }
}

// MARK: - AgoraRtcEngineDelegate
extension LiveRoomViewController: AgoraRtcEngineDelegate {
    // first local video frame
    func rtcEngine(_ engine: AgoraRtcEngineKit, firstLocalVideoFrameWith size: CGSize, elapsed: Int) {
        if let selfSession = videoSessions.first {
            selfSession.updateInfo(resolution: size)
        }
    }
    
    // local stats
    func rtcEngine(_ engine: AgoraRtcEngineKit, reportRtcStats stats: AgoraChannelStats) {
        if let selfSession = videoSessions.first {
            selfSession.updateChannelStats(stats)
        }
    }
    
    // first remote video frame
    func rtcEngine(_ engine: AgoraRtcEngineKit, firstRemoteVideoDecodedOfUid uid: UInt, size: CGSize, elapsed: Int) {
        guard videoSessions.count <= maxVideoSession else {
            return
        }
        
        let userSession = videoSession(of: uid)
        userSession.updateInfo(resolution: size)
        agoraKit.setupRemoteVideo(userSession.canvas)
    }
    
    // user offline
    func rtcEngine(_ engine: AgoraRtcEngineKit, didOfflineOfUid uid: UInt, reason: AgoraUserOfflineReason) {
        var indexToDelete: Int?
        for (index, session) in videoSessions.enumerated() where session.uid == uid {
            indexToDelete = index
            break
        }
        
        if let indexToDelete = indexToDelete {
            let deletedSession = videoSessions.remove(at: indexToDelete)
            deletedSession.hostingView.removeFromSuperview()
            
            // release canvas's view
            deletedSession.canvas.view = nil
        }
    }
    
    // remote video stats
    func rtcEngine(_ engine: AgoraRtcEngineKit, remoteVideoStats stats: AgoraRtcRemoteVideoStats) {
        if let session = getSession(of: stats.uid) {
            session.updateVideoStats(stats)
        }
    }
    
    // remote audio stats
    func rtcEngine(_ engine: AgoraRtcEngineKit, remoteAudioStats stats: AgoraRtcRemoteAudioStats) {
        if let session = getSession(of: stats.uid) {
            session.updateAudioStats(stats)
        }
    }
    
    // warning code
    func rtcEngine(_ engine: AgoraRtcEngineKit, didOccurWarning warningCode: AgoraWarningCode) {
        print("warning code: \(warningCode.description)")
    }
    
    // error code
    func rtcEngine(_ engine: AgoraRtcEngineKit, didOccurError errorCode: AgoraErrorCode) {
        print("warning code: \(errorCode.description)")
    }
}
