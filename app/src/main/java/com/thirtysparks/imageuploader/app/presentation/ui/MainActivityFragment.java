package com.thirtysparks.imageuploader.app.presentation.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.thirtysparks.imageuploader.app.R;
import com.thirtysparks.imageuploader.app.presentation.presenter.MainActivityPresenter;
import com.thirtysparks.imageuploader.app.util.Util;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements MainView {
    private static final int  FILE_CODE = 0;

    @Bind(R.id.btn_select) TextView textView;
    @Bind(R.id.ll_images) LinearLayout imagesLinearLayout;

    private MainActivityPresenter presenter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, view);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This always works
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");
                getIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");

                startActivityForResult(chooserIntent, FILE_CODE);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter = new MainActivityPresenter();
        presenter.setView(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_CODE && resultCode == Activity.RESULT_OK) {
            presenter.processPickImageResult(data);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void displaySelectedImages(List<Uri> imagesList) {
        if(null != getActivity()) {
            for (Uri uri : imagesList) {
                ImageView iv = new ImageView(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.convertDipToPix(getResources().getDisplayMetrics(), 200));
                params.bottomMargin = Util.convertDipToPix(getResources().getDisplayMetrics(), 8);
                iv.setLayoutParams(params);
                Glide.with(this)
                        .load(uri)
                        .into(iv);
                imagesLinearLayout.addView(iv);
            }
        }
    }
}
