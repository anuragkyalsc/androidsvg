package com.caverock.androidsvgsample.fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvgsample.R;
import com.larvalabs.svgandroid.SVGParser;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Demonstrates how to render an SVG to a Bitmap object user SVG.renderToCanvas().
 *
 * For the SVG we are using the "Bouquet" emoji from the Google Noto Emoji font.
 * https://github.com/googlei18n/noto-emoji/blob/master/svg/emoji_u1f490.svg
 */
public class RenderToImageFragment extends Fragment
{
   private static final String  TAG = RenderToImageFragment.class.getSimpleName();

   private static final int    WIDTH = 500;
   private static final int    HEIGHT = 500;
   private static final int    NUM_BOUQUETS = 1;
   private static final float  SCALE_MIN = 0.1f;
   private static final float  SCALE_MAX = 0.5f;

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
   {
       return inflater.inflate(R.layout.content_single_image, container, false);
   }


   @Override
   public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
   {
      final TextView   titleView  = view.findViewById(R.id.title);
      final ImageView  imageView = view.findViewById(R.id.image);

      titleView.setText(R.string.title_render_to_image);

      // Load the SVG from the assets folder
      try {
         final SVG svg = SVG.getFromAsset(getActivity().getAssets(), "noto_emoji_u1f490.svg");
         // Se tab listener
         imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               Bitmap generatedImage = generateImage(svg);
               imageView.setImageBitmap(generatedImage);
            }
         });

         Bitmap  generatedImage = generateImage(svg);
         imageView.setImageBitmap(generatedImage);
      } catch (Exception e) {
         Log.e(TAG, "Could not load flower emoji SVG", e);
      }

   }


   private Bitmap  generateImage(SVG svg)
   {
      Bitmap  newBM = Bitmap.createBitmap((int) Math.ceil(svg.getDocumentWidth()),
              (int) Math.ceil(svg.getDocumentHeight()),
              Bitmap.Config.ARGB_8888);

      Canvas  bmcanvas = new Canvas(newBM);

      // Clear background to white
      bmcanvas.drawRGB(255, 255, 255);

      Log.d("AnuragTimer", "curr = " + SystemClock.currentThreadTimeMillis());
      // Render our document onto our canvas
      svg.renderToCanvas(bmcanvas);
      Log.d("AnuragTimer", "curr = " + SystemClock.currentThreadTimeMillis());

      return newBM;
   }

}
