package com.example.vehicletracker.`interface`

import com.google.android.gms.maps.model.LatLng
import java.lang.Math.*
import kotlin.math.pow

interface LatLngInterpolator {

    fun interpolate(fraction: Float, a: LatLng, b: LatLng): LatLng

    class Spherical : LatLngInterpolator {

        override fun interpolate(fraction: Float, a: LatLng, b: LatLng): LatLng {
            // http://en.wikipedia.org/wiki/Slerp
            val fromLat = toRadians(a.latitude)
            val fromLng = toRadians(a.longitude)
            val toLat = toRadians(b.latitude)
            val toLng = toRadians(b.longitude)
            val cosFromLat = cos(fromLat)
            val cosToLat = cos(toLat)

            // Computes Spherical interpolation coefficients.
            val angle = computeAngleBetween(fromLat, fromLng, toLat, toLng)
            val sinAngle = kotlin.math.sin(angle)
            if (sinAngle < 1E-6) {
                return a
            }
            val temp1 = kotlin.math.sin((1 - fraction) * angle) / sinAngle
            val temp2 = kotlin.math.sin(fraction * angle) / sinAngle

            // Converts from polar to vector and interpolate.
            val x = temp1 * cosFromLat * kotlin.math.cos(fromLng) + temp2 * cosToLat * kotlin.math.cos(
                toLng
            )
            val y = temp1 * cosFromLat * kotlin.math.sin(fromLng) + temp2 * cosToLat * kotlin.math.sin(
                toLng
            )
            val z = temp1 * kotlin.math.sin(fromLat) + temp2 * kotlin.math.sin(toLat)

            // Converts interpolated vector back to polar.
            val lat = kotlin.math.atan2(z, kotlin.math.sqrt(x * x + y * y))
            val lng = kotlin.math.atan2(y, x)
            return LatLng(toDegrees(lat), toDegrees(lng))
        }

        private fun computeAngleBetween(fromLat: Double, fromLng: Double, toLat: Double, toLng: Double): Double {
            val dLat = fromLat - toLat
            val dLng = fromLng - toLng
            return 2 * kotlin.math.asin(
                kotlin.math.sqrt(
                    sin(dLat / 2).pow(2.0) + kotlin.math.cos(fromLat) * kotlin.math.cos(toLat) * kotlin.math.sin(
                        dLng / 2
                    ).pow(2.0)
                )
            )
        }
    }
}