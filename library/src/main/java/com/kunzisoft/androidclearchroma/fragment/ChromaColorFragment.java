package com.kunzisoft.androidclearchroma.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kunzisoft.androidclearchroma.IndicatorMode;
import com.kunzisoft.androidclearchroma.OnColorSelectedListener;
import com.kunzisoft.androidclearchroma.R;
import com.kunzisoft.androidclearchroma.colormode.Channel;
import com.kunzisoft.androidclearchroma.colormode.ColorMode;
import com.kunzisoft.androidclearchroma.view.ChannelView;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * Created by joker on 16/02/17.
 */

public class ChromaColorFragment extends Fragment{

    private static final String TAG = "ChromaColorFragment";

    private AppCompatImageView colorView;

    public final static String ARG_INITIAL_COLOR = "arg_initial_color";
    public final static String ARG_COLOR_MODE_ID = "arg_color_mode_id";
    public final static String ARG_INDICATOR_MODE = "arg_indicator_mode";

    private @ColorInt int currentColor;
    private ColorMode colorMode = ColorMode.CMYK255;
    private IndicatorMode indicatorMode = IndicatorMode.DECIMAL;

    private OnColorSelectedListener onColorSelectedListener;

    public static ChromaColorFragment newInstance(@ColorInt int currentColor, ColorMode colorMode, IndicatorMode indicatorMode) {
        ChromaColorFragment chromaColorFragment = new ChromaColorFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_INITIAL_COLOR, currentColor);
        args.putSerializable(ARG_COLOR_MODE_ID, colorMode);
        args.putSerializable(ARG_INDICATOR_MODE, indicatorMode);

        chromaColorFragment.setArguments(args);

        return chromaColorFragment;
    }

    public static ChromaColorFragment newInstance(Bundle args) {
        ChromaColorFragment chromaColorFragment = new ChromaColorFragment();
        chromaColorFragment.setArguments(args);
        return chromaColorFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onColorSelectedListener = (OnColorSelectedListener) context;
        } catch(ClassCastException e) {
            Log.w(TAG, context.toString() + "doesn't implement" + OnColorSelectedListener.class.getSimpleName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.chroma_color_fragment, container, false);
        init((ViewGroup) root);

        return root;
    }

    private void init(ViewGroup root) {
        root.setClipToPadding(false);

        if(getArguments() != null) {
            assignArguments(getArguments());
        }
        if (currentColor == 0)
            currentColor = Color.GRAY;
        if (colorMode == null)
            colorMode = ColorMode.RGB;
        if (indicatorMode == null)
            indicatorMode = IndicatorMode.DECIMAL;

        colorView = (AppCompatImageView) root.findViewById(R.id.color_view);
        Drawable colorViewDrawable = new ColorDrawable(currentColor);
        colorView.setImageDrawable(colorViewDrawable);

        List<Channel> channels = colorMode.getColorMode().getChannels();
        final List<ChannelView> channelViews = new ArrayList<>();
        for(Channel channel : channels) {
            channelViews.add(new ChannelView(channel, currentColor, indicatorMode, getContext()));
        }

        ChannelView.OnProgressChangedListener seekBarChangeListener = new ChannelView.OnProgressChangedListener() {
            @Override
            public void onProgressChanged() {
                List<Channel> channels = new ArrayList<>();
                for(ChannelView chan : channelViews) {
                    channels.add(chan.getChannel());
                }
                currentColor = colorMode.getColorMode().evaluateColor(channels);
                // Listener for color selected in real time
                if(onColorSelectedListener!=null)
                    onColorSelectedListener.onColorSelected(currentColor);
                // Change view for visibility of color
                Drawable colorViewDrawable = new ColorDrawable(currentColor);
                colorView.setImageDrawable(colorViewDrawable);
            }
        };

        ViewGroup channelContainer = (ViewGroup) root.findViewById(R.id.channel_container);
        for(ChannelView c : channelViews) {
            channelContainer.addView(c);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) c.getLayoutParams();
            params.topMargin =
                    getResources().getDimensionPixelSize(R.dimen.channel_view_margin_top);
            params.bottomMargin =
                    getResources().getDimensionPixelSize(R.dimen.channel_view_margin_bottom);

            c.registerListener(seekBarChangeListener);
        }
    }

    private void assignArguments(Bundle args) {
        if(args.containsKey(ARG_INITIAL_COLOR))
            currentColor = args.getInt(ARG_INITIAL_COLOR);
        if(args.containsKey(ARG_COLOR_MODE_ID))
            colorMode = ColorMode.getColorModeFromId(args.getInt(ARG_COLOR_MODE_ID));
        if(args.containsKey(ARG_INDICATOR_MODE))
            indicatorMode = IndicatorMode.getIndicatorModeFromId(args.getInt(ARG_INDICATOR_MODE));
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);

        assignArguments(args);
    }

    public int getCurrentColor() {
        return currentColor;
    }

    public ColorMode getColorMode() {
        return colorMode;
    }

    public IndicatorMode getIndicatorMode() {
        return indicatorMode;
    }



}
