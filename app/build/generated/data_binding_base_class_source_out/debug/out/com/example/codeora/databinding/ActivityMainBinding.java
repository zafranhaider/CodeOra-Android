// Generated by view binder compiler. Do not edit!
package com.example.codeora.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.codeora.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final SwipeRefreshLayout swipeRefreshLayout;

  @NonNull
  public final WebView webview;

  private ActivityMainBinding(@NonNull ConstraintLayout rootView, @NonNull ConstraintLayout main,
      @NonNull ProgressBar progressBar, @NonNull SwipeRefreshLayout swipeRefreshLayout,
      @NonNull WebView webview) {
    this.rootView = rootView;
    this.main = main;
    this.progressBar = progressBar;
    this.swipeRefreshLayout = swipeRefreshLayout;
    this.webview = webview;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.progressBar;
      ProgressBar progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.swipeRefreshLayout;
      SwipeRefreshLayout swipeRefreshLayout = ViewBindings.findChildViewById(rootView, id);
      if (swipeRefreshLayout == null) {
        break missingId;
      }

      id = R.id.webview;
      WebView webview = ViewBindings.findChildViewById(rootView, id);
      if (webview == null) {
        break missingId;
      }

      return new ActivityMainBinding((ConstraintLayout) rootView, main, progressBar,
          swipeRefreshLayout, webview);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
