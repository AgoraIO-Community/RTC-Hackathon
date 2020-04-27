//
//  HeartView.swift
//  OpenLive
//
//  Created by Jack💕10 on 2020/4/21.
//  Copyright © 2020 Jane Jack. All rights reserved.
//

import UIKit
import Foundation

//MARK:- Themes
struct HeartTheme {
    let fillColor: UIColor
    let strokeColor: UIColor
    //using white borders for this example. Set your colors.
    static let availableThemes = [
        (UIColor(hex: 0xe66f5e), UIColor(white: 1.0, alpha: 0.8)),
        (UIColor(hex: 0x6a69a0), UIColor(white: 1.0, alpha: 0.8)),
        (UIColor(hex: 0x81cc88), UIColor(white: 1.0, alpha: 0.8)),
        (UIColor(hex: 0xfd3870), UIColor(white: 1.0, alpha: 0.8)),
        (UIColor(hex: 0x6ecff6), UIColor(white: 1.0, alpha: 0.8)),
        (UIColor(hex: 0xc0aaf7), UIColor(white: 1.0, alpha: 0.8)),
        (UIColor(hex: 0xf7603b), UIColor(white: 1.0, alpha: 0.8)),
        (UIColor(hex: 0x39d3d3), UIColor(white: 1.0, alpha: 0.8)),
        (UIColor(hex: 0xfed301), UIColor(white: 1.0, alpha: 0.8))
    ]
    ///return random theme from selection above
    static func randomTheme() -> HeartTheme {
        let r = Int(randomNumber(cap: availableThemes.count))
        return HeartTheme(fillColor: availableThemes[r].0, strokeColor: availableThemes[r].1)
    }
}


//MARK:- HeartView
let PI = CGFloat(Double.pi)

class HeartView: UIView {

    override init(frame: CGRect) {
        super.init(frame: frame)
        
        backgroundColor = UIColor.clear
        layer.anchorPoint = CGPoint(x: 0.5, y: 1) //mid-bottom
    }

    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    ///Perform the floating heart animation.
    ///Many of the values were adjusted to feel as nice and as close as possible to Periscope's.
    ///You should play around with different values to suit your specific use-case.
    func animateInView(view: UIView) {
        
        let totalAnimationDuration: TimeInterval = 6
        let heartSize = self.bounds.width
        let heartCenterX = self.center.x
        let viewHeight = view.bounds.height
        
        //Pre-Animation setup
        self.transform = CGAffineTransform(scaleX: 0, y: 0)
        self.alpha = 0
        
        //Bloom
        spring(duration: 0.5, delay: 0.0, damping: 0.6, velocity: 0.8) {
            self.transform = .identity
            self.alpha = 0.9
        }

        //Slight rotation
        let rotationDirection: Int = (1 - Int(2*randomNumber(cap: 2))) // -1 OR 1
        let rotationFraction = randomNumber(cap: 10)
        UIView.animate(withDuration: totalAnimationDuration) {
            self.transform = CGAffineTransform(rotationAngle: CGFloat(rotationDirection) * PI/(16 + rotationFraction*0.2))
        }
        
        
        //Travel along path
        let heartTravelPath = UIBezierPath()
        heartTravelPath.move(to: self.center)
        
        //random end point
        let endPoint = CGPoint(x: heartCenterX + (CGFloat(rotationDirection) * randomNumber(cap: 2*heartSize)), y: viewHeight/6.0 + randomNumber(cap: viewHeight/4.0))
        
        //random Control Points
        let travelDirection: Int = (1 - Int(2*randomNumber(cap: 2))) // -1 OR 1
        
        //randomize x and y for control points
        let xDelta = (heartSize/2.0 + randomNumber(cap: 2*heartSize)) * CGFloat(travelDirection)
        let yDelta = max(endPoint.y ,max(randomNumber(cap: 8*heartSize), heartSize))
        
        let controlPoint1 = CGPoint(x: heartCenterX + xDelta, y: viewHeight - yDelta)
        let controlPoint2 = CGPoint(x: heartCenterX - 2*xDelta, y: yDelta)
        
        heartTravelPath.addCurve(to: endPoint, controlPoint1: controlPoint1, controlPoint2: controlPoint2)
        
        let keyFrameAnimation = CAKeyframeAnimation(keyPath: "position")
        keyFrameAnimation.path = heartTravelPath.cgPath
        keyFrameAnimation.timingFunction = CAMediaTimingFunction(name: CAMediaTimingFunctionName.linear)
        keyFrameAnimation.duration = totalAnimationDuration + TimeInterval(endPoint.y/viewHeight)
        self.layer.add(keyFrameAnimation, forKey: "positionOnPath")
        
        
        //Alpha
        UIView.animate(withDuration: totalAnimationDuration, delay: 0,
            animations: {
                self.alpha = 0.0
            },
            completion: {
                (value: Bool) in
                self.removeFromSuperview()
            }
        )
    }
    
    
    override func draw(_ rect: CGRect) {

#if false

        let theme = HeartTheme.randomTheme()
    
        let heartImage = UIImage(named: "heart")
        let heartImageBorder = UIImage(named: "heartBorder")
    
        //Draw background image (mimics border)
        theme.strokeColor.setFill()
        heartImageBorder?.draw(in: rect, blendMode: .normal, alpha: 1.0)

        //Draw foreground heart image
        theme.fillColor.setFill()
        heartImage?.draw(in: rect, blendMode: .normal, alpha: 1.0)
#else
        //Just for fun. Draw heart using Bezier path
        drawHeartInRect(rect: rect)
#endif
    }
    
    private func drawHeartInRect(rect: CGRect) {
        
        let theme = HeartTheme.randomTheme()

        theme.strokeColor.setStroke()
        theme.fillColor.setFill()
        
        let drawingPadding: CGFloat = 4.0
        let curveRadius = floor((rect.width - 2*drawingPadding) / 4.0)
        
        //Creat path
        let heartPath = UIBezierPath()
        
        //Start at bottom heart tip
        let tipLocation = CGPoint(x: floor(rect.width / 2.0), y: rect.height - drawingPadding)
        heartPath.move(to: tipLocation)
        
        //Move to top left start of curve
        let topLeftCurveStart = CGPoint(x: drawingPadding, y: floor(rect.height / 2.4))
        heartPath.addQuadCurve(to: topLeftCurveStart, controlPoint: CGPoint(x: topLeftCurveStart.x, y: topLeftCurveStart.y + curveRadius))
        
        //Create top left curve
        heartPath.addArc(withCenter: CGPoint(x: topLeftCurveStart.x + curveRadius, y: topLeftCurveStart.y), radius: curveRadius, startAngle: PI, endAngle: 0, clockwise: true)
        
        //Create top right curve
        let topRightCurveStart = CGPoint(x: topLeftCurveStart.x + 2*curveRadius, y: topLeftCurveStart.y)
        heartPath.addArc(withCenter: CGPoint(x: topRightCurveStart.x + curveRadius, y: topRightCurveStart.y), radius: curveRadius, startAngle: PI, endAngle: 0, clockwise: true)
        
        //Final curve to bottom heart tip
        let topRightCurveEnd = CGPoint(x: topLeftCurveStart.x + 4*curveRadius, y: topRightCurveStart.y)
        heartPath.addQuadCurve(to: tipLocation, controlPoint: CGPoint(x: topRightCurveEnd.x, y: topRightCurveEnd.y + curveRadius))
        
        heartPath.fill()
        
        heartPath.lineWidth = 1
        heartPath.lineCapStyle = .round
        heartPath.lineJoinStyle = .round
        heartPath.stroke()
    }
}
