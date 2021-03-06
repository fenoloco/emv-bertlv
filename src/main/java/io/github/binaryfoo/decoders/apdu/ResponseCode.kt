package io.github.binaryfoo.decoders.apdu

import io.github.binaryfoo.res.ClasspathIO
import org.apache.commons.io.IOUtils
import org.apache.commons.lang.builder.ToStringBuilder
import org.apache.commons.lang.builder.ToStringStyle

import java.io.IOException
import java.io.InputStream
import java.util.ArrayList
import kotlin.platform.platformStatic

/**
 * Maps the two status words (SW1, SW2) included in an R-APDU to a type and description.
 */
public data class ResponseCode(val sw1: String, val sw2: String, val `type`: String, val description: String) {

    public fun getHex(): String {
        return sw1 + sw2
    }

    class object {

        private val codes = ClasspathIO.readLines("r-apdu-status.txt").map { line ->
            ResponseCode(line.substring(0, 2), line.substring(3, 5), line.substring(6, 7), line.substring(8))
        }

        platformStatic public fun lookup(hex: String): ResponseCode {
            val sw1 = hex.substring(0, 2)
            val sw2 = hex.substring(2, 4)
            for (code in codes) {
                if (sw1 == code.sw1) {
                    if ("XX" == code.sw2) {
                        return code
                    }
                    if ("--" == code.sw2) {
                        continue
                    }
                    if (sw2 == code.sw2) {
                        return code
                    }
                }
            }
            return ResponseCode(sw1, sw2, "", "Unknown")
        }
    }
}
