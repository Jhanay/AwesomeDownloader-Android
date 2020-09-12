package com.jiang.awesomedownloader

/**
 *
 * @ProjectName:    AwesomeDownloader
 * @ClassName:      BaseDownloadListener
 * @Description:     java类作用描述
 * @Author:         江
 * @CreateDate:     2020/8/27 22:29
 */
interface BaseDownloadListener {
    fun onProgressChange(progress: Long)
    fun onFinish(downloadBytes: Long, totalBytes: Long)
    fun onStop(downloadBytes: Long, totalBytes: Long)
}