package com.zero.game.utils.frame

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Point
import android.util.Log
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark
import com.zero.game.repository.model.FaceState
import okhttp3.internal.and
import kotlin.math.pow
import kotlin.math.sqrt

class VideoFrameHandler {

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("apm-plugin-packet-processing")
        }
    }

    private var callback: IOnCallback? = null
    private val highAccuracyOpts = FirebaseVisionFaceDetectorOptions.Builder()
            .setPerformanceMode(FirebaseVisionFaceDetectorOptions.FAST)
            .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
            .setContourMode(FirebaseVisionFaceDetectorOptions.NO_CONTOURS)
            .setClassificationMode(FirebaseVisionFaceDetectorOptions.NO_CLASSIFICATIONS)
            .build()
    private var handling = false
    private var onCapture: IOnCapture? = null


    fun register(c: IOnCallback) {
        callback = c
        doRegisterProcessing()
    }

    fun unRegister() {
        doUnregisterProcessing()
    }

    fun onSuccess(string: String) {
        Log.i("VideoFrameHandler", "ldh onSuccess $string")
    }

    fun onSend(bytes: ByteArray, width: Int, height: Int, rotation: Int) {
        if (!handling) {
            Log.i("VideoFrameHandler", "ldh onSend ${bytes.size}")
            val color = rgbToARGB(bytes, width, height)
            val bitmap = Bitmap.createBitmap(
                color,
                width,
                height,
                Bitmap.Config.ARGB_8888
            )
            val matrix = Matrix()
            matrix.postRotate(rotation.toFloat())
            matrix.postScale(-1.0f, 1.0f)
            val rotationBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
            if (onCapture != null) {
                onCapture?.onCapture(rotationBitmap)
                onCapture = null
            }
            //callback?.onBitmap(rotationBitmap)
            handleFace2(rotationBitmap)
        }
    }

    private fun handleFace2(bitmap: Bitmap) {
        handling = true
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detect = FirebaseVision.getInstance().getVisionFaceDetector(highAccuracyOpts)
        detect.detectInImage(image)
            .addOnSuccessListener {
                Log.i("ldh", "face detect ${it.size}")
                var rightHalfFace = 0.0
                var leftHalfFace = 0.0
                var topHalfFace = 0.0
                var bottomHalfFace = 0.0
                var ratio = -1.0
                var ratioV = -1.0
                if (it.size != 0) {
                    for (face in it) {
                        val leftEye = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EYE)
                        val rightEye = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EYE)
                        val nose = face.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE)
                        val mouth = face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_BOTTOM)
                        val pointLeft = Point(
                            leftEye?.position?.x?.toInt() ?: 0,
                            leftEye?.position?.y?.toInt() ?: 0
                        )
                        val pointRight = Point(
                            rightEye?.position?.x?.toInt() ?: 0,
                            rightEye?.position?.y?.toInt() ?: 0
                        )
                        val pointNose = Point(
                            nose?.position?.x?.toInt() ?: 0,
                            nose?.position?.y?.toInt() ?: 0
                        )
                        val pointMouth = Point(
                            mouth?.position?.x?.toInt() ?: 0,
                            mouth?.position?.y?.toInt() ?: 0
                        )
                        val eyeCenter = Point(
                            (pointLeft.x + pointRight.x) / 2,
                            (pointLeft.y + pointRight.y) / 2
                        )

                        rightHalfFace = euclidean(pointNose, pointRight)
                        leftHalfFace = euclidean(pointNose, pointLeft)
                        topHalfFace = euclideanY(pointNose, eyeCenter)
                        bottomHalfFace = euclideanY(pointNose, pointMouth)
                        ratio =
                            rightHalfFace.coerceAtMost(leftHalfFace) / rightHalfFace.coerceAtLeast(
                                leftHalfFace
                            )
                        ratioV =
                            topHalfFace.coerceAtMost(bottomHalfFace) / topHalfFace.coerceAtLeast(
                                bottomHalfFace
                            )

                        val faceState = if (ratio == -1.0) {
                            FaceState.NONE
                        } else if (ratio > 0.7 && ratio < 1) {
                            FaceState.FRONT
                        } else { //if your right face is bigger than the left, then your head toward left
                            if (rightHalfFace > leftHalfFace) {
                                FaceState.LEFT
                            } else {
                                FaceState.RIGHT
                            }
                        }
                        val vFaceState = if (ratioV == -1.0) {
                            FaceState.NONE
                        } else if (bottomHalfFace < topHalfFace) {
                            FaceState.BOTTOM
                        } else if (ratioV > 0.7 && ratioV <= 1) {
                            FaceState.FRONT
                        } else { //if your right face is bigger than the left, then your head toward left
                            FaceState.TOP
                        }
                        callback?.onFaceState(faceState)
                        callback?.onVFaceState(vFaceState)
                    }
                } else {
                    callback?.onFaceState(FaceState.NONE)
                    callback?.onVFaceState(FaceState.NONE)
                }
                handling = false
            }
            .addOnFailureListener {
                Log.i("ldh", "face not found")
                callback?.onFaceState(FaceState.NONE)
                callback?.onVFaceState(FaceState.NONE)
                handling = false
            }

    }

    fun isHandling(): Boolean {
        return handling;
    }

    private fun euclidean(p1: Point, p2: Point): Double {
        return sqrt(
            (p1.x - p2.x).toDouble().pow(2.0) + (p1.y - p2.y).toDouble().pow(2.0)
        )
    }

    private fun euclideanY(p1: Point, p2: Point): Double {
        return sqrt((p1.y - p2.y).toDouble().pow(2.0))
    }

    private fun rgbToARGB(bytes: ByteArray, width: Int, height: Int): IntArray {
        val pix = IntArray(bytes.size / 4)
        for (i in 0 until height) {
            for (j in 0 until width) {
                val idx = width * i + j
                val rgbIdx = idx * 4
                val red = bytes[rgbIdx]
                val green = bytes[rgbIdx + 1]
                val blue = bytes[rgbIdx + 2]
                val alpha = bytes[rgbIdx + 3]
                val color =
                    (blue and 0x000000FF) or (green.toInt().shl(8) and 0x0000FF00) or (red.toInt()
                        .shl(
                            16
                        ) and 0x00FF0000) or (alpha.toInt().shl(24) and 0xFF000000.toInt())
                pix[idx] = color
            }
        }
        return pix
    }

    fun capture(onCapture: IOnCapture) {
        this.onCapture = onCapture
    }

    private external fun doRegisterProcessing()
    private external fun doUnregisterProcessing()

    interface IOnCallback {
        fun onBitmap(bitmap: Bitmap)
        fun onFaceState(faceState: FaceState)
        fun onVFaceState(faceState: FaceState)
    }

    interface IOnCapture {
        fun onCapture(bitmap: Bitmap)
    }
}