package com.example.tripview.view

import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberImagePainter
import com.example.tripview.viewmodel.PexelsViewModel
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.library.BuildConfig
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.tileprovider.tilesource.TileSourceFactory



@Composable
fun PexelsSearchScreen(
    viewModel: PexelsViewModel,
    modifier: Modifier = Modifier
) {

    val prefecture by viewModel.prefecture
    val city by viewModel.city

    val context = LocalContext.current

    val mapView = rememberMapViewWithLifecycle()


    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            AndroidView(
                factory = {
                    mapView.apply {
                        isTilesScaledToDpi = true
                        setTileSource(TileSourceFactory.MAPNIK)
                        setMultiTouchControls(true)
                        setBuiltInZoomControls(false)
                        controller.setZoom(3.5)
                        controller.setCenter(GeoPoint(50.689, 15.692))

                        minZoomLevel = 2.0
                        maxZoomLevel = 20.0

                        setLayerType(View.LAYER_TYPE_HARDWARE, null)

                        val mapEventsReceiver = object : MapEventsReceiver {
                            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                                p?.let {
                                    viewModel.updateCoordinatesAndSearchPhotos(
                                        it.latitude,
                                        it.longitude
                                    )
                                }
                                return true
                            }

                            override fun longPressHelper(p: GeoPoint?): Boolean {
                                return false
                            }
                        }

                        val mapEventsOverlay = MapEventsOverlay(mapEventsReceiver)
                        overlays.add(mapEventsOverlay)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
            )


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
                    .background(Color.White.copy(alpha = 0.5f))
            ) {
                Text(
                    "Prefecture: $prefecture",
                    fontSize = 15.sp
                )
                Text(
                    "City: $city",
                    fontSize = 15.sp
                )
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(viewModel.photos) { photo ->
                        Image(
                            painter = rememberImagePainter(photo.urls.landscape),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        )
                    }
                }
            }
        }
    }
}