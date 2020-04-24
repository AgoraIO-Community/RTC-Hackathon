# Concentration Analysis (Camera/Video Part)

This repo takes advantages of OpenCV and TensorFlow to achieve the goal of concentration analysis (camera/video part).

If you prefer Chinese README, please refer to [this file](https://github.com/MercurialJD/Concentration-Detection/blob/master/README_zh.md).

![demo](https://github.com/MercurialJD/Concentration-Detection/raw/master/img/output.gif)

**Note: The original video before being processed is referred from the Internet**

## Prerequisites

- OpenCV
- TensorFlow
- Python 3
- Codes from this repo has been tested on Ubuntu 18.04

## Installation

```bash
git clone https://github.com/MercurialJD/Concentration-Detection.git
```

## User's Guidance

This project accepts video files or real-time camera records. In the case no argument is provided or the given argument points to a camera, the corresponding camera will be automatically invoked; If a video file is given as an argument, the video will be analyzed.

### Video Mode

For video formats that can be identified by OpenCV:

```bash
python3 concentration_estimation.py --video /path/to/your/video.mp4
```

### Camera Mode

Default camera:

```
python3 concentration_estimation.py
```

Explicitly designated camera:

```bash
python3 concentration_estimation.py --cam 0
```

## Output Adjustments

Please adjust `concentration_estimation.py` w.r.t. your requirements.

- If the processed video is needed, please uncomment following lines:

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

- If real-tile preview is needed, please uncomment the following line:

  ```
  # cv2.imshow("Preview", frame)
  ```

- If the facial identification box need to be customized, please modify:

  ```
  pose_estimator.draw_annotation_box(...)
  ```

- If head-pose axes need to be customized, please modify:

  ```
  pose_estimator.draw_axis(...)
  ```

## Basic Procedure

1. Facial recognition with DNN module provided by OpenCV
2. Facial landmarks with 68-point estimation provided by TensorFlow
3. Pose estimation with PnP algorithm
4. Stabilization with Kalman filter
5. Concentration analysis

## License

Please refer to [LICENSE.md](https://github.com/MercurialJD/Concentration-Detection/blob/master/LICENSE).

## Acknowledgements

- [**Fine-Grained Head Pose Estimation Without Keypoints (CVPR 2018)**](https://arxiv.org/abs/1710.00925) by Ruiz, Nataniel and Chong, Eunji and Rehg, James M.
- **Head pose estimation by TensorFlow and OpenCV** by [Yin Guobing](https://yinguobing.com/)
