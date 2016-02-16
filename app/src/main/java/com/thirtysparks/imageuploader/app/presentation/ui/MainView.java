package com.thirtysparks.imageuploader.app.presentation.ui;

import android.net.Uri;

import java.util.List;

/**
 * Created by Ryan on 16/2/2016.
 */
public interface MainView extends LoadDataView {
    void displaySelectedImages(List<Uri> imagesList);
}
