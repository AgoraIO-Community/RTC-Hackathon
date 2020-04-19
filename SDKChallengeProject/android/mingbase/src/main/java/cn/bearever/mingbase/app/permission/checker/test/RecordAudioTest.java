/*
 * Copyright © Zhenjie Yan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.bearever.mingbase.app.permission.checker.test;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * Created by Zhenjie Yan on 2018/1/14.
 */
class RecordAudioTest implements PermissionTest {

    private Context mContext;

    RecordAudioTest(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean test() throws Throwable {
        AudioRecord audioRecord = findAudioRecord();
        try {
            if (audioRecord != null) {
                audioRecord.startRecording();
            } else {
                return !existMicrophone(mContext);
            }
        } catch (Throwable e) {
            return !existMicrophone(mContext);
        } finally {
            if (audioRecord != null) {
                audioRecord.stop();
                audioRecord.release();
            }
        }
        return true;
    }

    private static boolean existMicrophone(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
    }

    private static AudioRecord findAudioRecord() {
        int sampleRateInHz = 8000;
        int channelConfig = AudioFormat.CHANNEL_IN_MONO;
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
        int buffer = AudioRecord.getMinBufferSize(
                sampleRateInHz, channelConfig, audioFormat);
        if (buffer != AudioRecord.ERROR_BAD_VALUE) {
            AudioRecord recorder = new AudioRecord(
                    MediaRecorder.AudioSource.MIC,
                    sampleRateInHz, channelConfig, audioFormat,
                    buffer);
            if (recorder.getState() == AudioRecord.STATE_INITIALIZED) {
                return recorder;
            }
        }
        return null;
    }

}