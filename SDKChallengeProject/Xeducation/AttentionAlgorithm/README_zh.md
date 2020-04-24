# 专注度分析（摄像头/视频部分）

本项目通过使用OpenCV与TensorFlow来达到专注度分析（摄像头/视频部分）的目的。

![demo](https://github.com/MercurialJD/Concentration-Detection/raw/master/img/output.gif)

**注意：原始视频资源来自网络**

## 环境需求

- OpenCV
- TensorFlow
- Python 3
- 本项目已在Ubuntu 18.04完成测试

## 安装

```bash
git clone https://github.com/MercurialJD/Concentration-Detection.git
```

## 使用

本项目可以识别视频文件或摄像头输入数据。在无参数或有摄像头参数的情况下将自动调用摄像头；如指明视频文件，将对视频文件进行分析。

### 视频模式

对于可被OpenCV识别的视频格式:

```bash
python3 concentration_estimation.py --video /path/to/your/video.mp4
```

### 摄像头模式

调用默认摄像头：

```
python3 concentration_estimation.py
```

或指定摄像头:

```bash
python3 concentration_estimation.py --cam 0
```

## 输出设置

请根据需求对`concentration_estimation.py`作相应调整：

- 如需获取处理后的视频，请将以下代码前的`#`去除：

  ```
  # fourcc = cv2.VideoWriter_fourcc(*'mp4v')
  # out = cv2.VideoWriter('output.mp4', fourcc, 20.0, (width, height))
  ...
  # cv2.putText(frame, "FACING", (height//50, width//50), cv2.FONT_HERSHEY_DUPLEX, 1, (0, 0, 0), 1)
  ...
  # cv2.putText(frame, "UNFACED", (height//50, width//50), cv2.FONT_HERSHEY_DUPLEX, 1, (0, 0, 0), 1)
  ...
  # out.write(frame)
  ...
  # out.release()
  ```

- 如需实时预览，请将以下代码前的`#`去除：

  ```
  # cv2.imshow("Preview", frame)
  ```

- 如需自定义面部识别框，请修改：

  ```
  pose_estimator.draw_annotation_box(...)
  ```

- 如需自定义姿态轴，请修改：

  ```
  pose_estimator.draw_axis(...)
  ```

## 基本流程

1. 面部识别 - 使用OpenCV中DNN模块
2. 面部标记 - 使用TensorFlow中68点估计
3. 姿态估计 - PnP算法
4. 稳定化 - Kalman filter
5. 专注度分析

## 许可

请查看[LICENSE.md](https://github.com/MercurialJD/Concentration-Detection/blob/master/LICENSE)。

## 鸣谢

- [**Fine-Grained Head Pose Estimation Without Keypoints (CVPR 2018)**](https://arxiv.org/abs/1710.00925) by Ruiz, Nataniel and Chong, Eunji and Rehg, James M.
- **Head pose estimation by TensorFlow and OpenCV** by [Yin Guobing](https://yinguobing.com/)
