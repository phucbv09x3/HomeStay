package com.kujira.homestay.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigator
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.compat.GeoDataClient
import com.google.android.libraries.places.compat.PlaceDetectionClient
import com.kujira.homestay.R
import com.kujira.homestay.databinding.ActivityMapBinding
import com.kujira.homestay.ui.base.BaseActivity
import com.kujira.homestay.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_map.*

class MapActivity : BaseActivity<MapViewModel,ActivityMapBinding>(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private var mListLocation: MutableList<String>? = null
    private var checkMapType = false
    private var mGeoDataClient: GeoDataClient? = null
    private var mPlaceDetectionClient: PlaceDetectionClient? = null
    private var mFusedLocationProvider: FusedLocationProviderClient? = null
    private var locationPermissionGranted = false
    private var mMapViewModel: MapViewModel? = null
    private var lastKnownLocation: String? = null
    private var mMarker: Marker? = null
    private var mMarker2: Marker? = null
    private var polyline: Polyline? = null

    override fun createViewModel(): Class<MapViewModel> {
        return  MapViewModel::class.java
    }

    override fun getContentView(): Int =R.layout.activity_map

    override fun initAction() {

    }

    override fun initData() {
        val supportMapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment?.getMapAsync(this)
        mMapViewModel = MapViewModel()
        init()
        searchLocationOnEdt()
        btn_goLine.setOnClickListener {
            val origin = edt_go.text.toString().trim()
            val destination = edt_go.text.toString().trim()
            mMapViewModel?.getDirection(origin, destination)
        }
        mListLocation = mutableListOf()
        mMapViewModel?.direction?.observe(this,  {
            Log.d("listPoitAPi","${it}")
            if (it != null) {
                mMarker?.remove()
                mMarker2?.remove()
                polyline?.remove()
                val point = decodePolyLine(it.routes[0].overview_polyline.points)
                val origin = it.routes[0].legs[0].start_location
                val locationStart = LatLng(origin.lat, origin.lng)
                val destination = it.routes[0].legs[0].end_location
                val locationEnd = LatLng(destination.lat, destination.lng)
                val markerStart = MarkerOptions().position(locationStart)
                    .title(it.routes[0].legs[0].start_address)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))

                val markerEnd = MarkerOptions().position(locationEnd)
                    .title(it.routes[0].legs[0].end_address)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                mMarker = mMap.addMarker(markerStart)
                mMarker2 = mMap.addMarker(markerEnd)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationStart, 9f))
                if (point != null) {
                    var pOption = PolylineOptions()
                    pOption.addAll(point)
                    pOption.color(Color.BLUE)
                    pOption.width(6f)
                    pOption.geodesic(true)
                    polyline = mMap.addPolyline(pOption)
                }
            }
        })
    }

    override fun onFragmentResumed(fragment: BaseFragment<*, *>) {

    }

    override fun navigate(fragmentId: Int, bundle: Bundle?, addToBackStack: Boolean) {

    }

    override fun navigateWithSharedElement(
        fragmentId: Int,
        bundle: Bundle?,
        sharedElements: FragmentNavigator.Extras?,
        addToBackStack: Boolean
    ) {

    }

    override fun navigateUp() {

    }

    override fun present(fragmentId: Int, bundle: Bundle?) {

    }


    private fun searchLocationOnEdt() {
        edt_go.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                mMapViewModel?.searchLocation(edt_go.text.toString().trim())
                edt_go.showDropDown()
            }

        })
        edt_to.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                mMapViewModel?.searchLocation(edt_to.text.toString().trim())
                edt_to.showDropDown()
            }

        })

        mMapViewModel?.searchLocation?.observe(this, Observer {
            if (it != null) {
                Log.d("listPOit","${it}")
                mListLocation?.removeAll(mListLocation!!)
                for (item in it.predictions) {
                    mListLocation?.add(item.description)
                }

                val adapter = ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    mListLocation!!
                )
//                val polyline1 = mMap.addPolyline(PolylineOptions()
//                    .clickable(true)
//                    .add(
//                        mListLocation
//                    )
//                )
//                polyline1.tag='A'
                edt_go.setAdapter(adapter)
                edt_to.setAdapter(adapter)


            }
        })
    }

    private fun init() {
        mGeoDataClient = com.google.android.libraries.places.compat.Places.getGeoDataClient(this)
        mPlaceDetectionClient =
            com.google.android.libraries.places.compat.Places.getPlaceDetectionClient(this)
        mFusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!
        val pos = LatLng(20.9788, 105.7973)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 16f))
        mMap.addMarker(
            MarkerOptions()
                .position(pos)
                .title("201 Đường Chiến Thắng-Tân Triều-Thanh Trì")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pushpin))
        )


    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigateUp()
    }

    private fun decodePolyLine(poly: String): List<LatLng>? {
        val len = poly.length
        var index = 0
        val decoded: MutableList<LatLng> = ArrayList()
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = poly[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlatlg = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlatlg
            shift = 0
            result = 0
            do {
                b = poly[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            decoded.add(
                LatLng(
                    lat / 100000.0, lng / 100000.0
                )
            )
        }
        return decoded
    }
}