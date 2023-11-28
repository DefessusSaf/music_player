@file:Suppress("DEPRECATION")

package com.example.music_pleer

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur

@Suppress("DEPRECATION")
class Blur {
    companion object{
        fun blurBitmap(context: Context, image: Bitmap): Bitmap {
            val bitmap = image.copy(image.config, true)
            val rs = RenderScript.create(context)
            val input = Allocation.createFromBitmap(rs, bitmap)
            val output = Allocation.createTyped(rs, input.type)
            val script = ScriptIntrinsicBlur.create(rs, input.element)

            script.setRadius(25f)
            script.setInput(input)
            script.forEach(output)
            output.copyTo(bitmap)

            rs.destroy()

            return bitmap
        }
    }
}