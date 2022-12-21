// Generated code from Butter Knife. Do not modify!
package com.simadanesh.bundlemanagement.truckloading;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class TruckLoadManualDialog$$ViewInjector<T extends com.simadanesh.bundlemanagement.truckloading.TruckLoadManualDialog> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624108, "field 'panelBarcode'");
    target.panelBarcode = finder.castView(view, 2131624108, "field 'panelBarcode'");
    view = finder.findRequiredView(source, 2131624111, "field 'panelWeight'");
    target.panelWeight = finder.castView(view, 2131624111, "field 'panelWeight'");
    view = finder.findRequiredView(source, 2131624113, "field 'showBarcode' and method 'showBarcode'");
    target.showBarcode = finder.castView(view, 2131624113, "field 'showBarcode'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.showBarcode();
        }
      });
    view = finder.findRequiredView(source, 2131624110, "field 'showWeight' and method 'showWeight'");
    target.showWeight = finder.castView(view, 2131624110, "field 'showWeight'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.showWeight();
        }
      });
    view = finder.findRequiredView(source, 2131624109, "field 'barcodeTxt'");
    target.barcodeTxt = finder.castView(view, 2131624109, "field 'barcodeTxt'");
    view = finder.findRequiredView(source, 2131624112, "field 'weightTxt'");
    target.weightTxt = finder.castView(view, 2131624112, "field 'weightTxt'");
    view = finder.findRequiredView(source, 2131624114, "method 'onOK'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onOK();
        }
      });
    view = finder.findRequiredView(source, 21