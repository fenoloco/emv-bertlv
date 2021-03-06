package io.github.binaryfoo.bit

import io.github.binaryfoo.tlv.ISOUtil
import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder
import java.util.TreeSet

/**
 * EMV specs seem to follow the convention: bytes are numbered left to right, bits are numbered byte right to left,
 * both start at 1.
 */
public data class EmvBit(public val byteNumber: Int, public val bitNumber: Int, public val set: Boolean) : Comparable<EmvBit> {

    public val value: String
    get() = if (set) "1" else "0"

    override fun toString(): String = toString(true)

    public fun toString(includeComma: Boolean, includeValue: Boolean = true): String {
        val separator = if (includeComma) "," else ""
        if (includeValue) {
            return "Byte ${byteNumber}${separator} Bit ${bitNumber} = ${value}"
        }
        return "Byte ${byteNumber}${separator} Bit ${bitNumber}"
    }

    override fun compareTo(other: EmvBit): Int {
        val byteOrder = byteNumber.compareTo(other.byteNumber)
        if (byteOrder != 0) {
            return byteOrder
        }
        val bitOrder = other.bitNumber.compareTo(bitNumber)
        if (bitOrder != 0) {
            return bitOrder
        }
        return set.compareTo(other.set)
    }
}

/**
 * Label set bits (those = 1) in hex.
 */
public fun labelFor(hex: String): String {
    return fromHex(hex).toString(includeValue = false)
}
