/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.glassx.wear.juju.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.wearable.view.FragmentGridPagerAdapter;

import cn.glassx.wear.juju.R;
import cn.glassx.wear.juju.widget.JUJUerDetailFirst;
import cn.glassx.wear.juju.widget.SimpleFragment;

/**
 * Constructs fragments as requested by the GridViewPager. For each row a
 * different background is provided.
 */
public class GridPagerAdapter extends FragmentGridPagerAdapter {

    private final Context mContext;

    public GridPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        mContext = ctx;
    }

    /** A simple container for static data in each page */
    private static class Page {
        int titleRes;
        int iconRes;
        int backgroundRes;
        int backgroundColor;

        public Page(int titleRes, int iconRes){
            this(titleRes,iconRes,0,0);
        }

        public Page(int titleRes, int iconRes, int backgroundRes){
            this(titleRes,iconRes,backgroundRes,0);
        }

        public Page(int titleRes, int iconRes, int backgroundRes, int backgroundColor){
            this.titleRes = titleRes;
            this.iconRes = iconRes;
            this.backgroundColor = backgroundColor;
            this.backgroundRes = backgroundRes;
        }

    }

    private final Page[][] PAGES = {
            {
               new Page(0,0),
               new Page(R.string.send_paper,R.drawable.portrail),
               new Page(R.string.attention,R.drawable.portrail),
               new Page(R.string.open_in_phone,R.drawable.portrail)
            }
    };

    @Override
    public Fragment getFragment(int row, int col) {
        if(row == 0 && col == 0){
            return new JUJUerDetailFirst();
        }
        Page page = PAGES[row][col];
        String title = page.titleRes != 0 ? mContext.getString(page.titleRes) : null;
        Drawable icon = page.iconRes != 0 ? mContext.getResources().getDrawable(page.iconRes) : null;
        SimpleFragment simpleFragment = SimpleFragment.create(title,icon);
        return simpleFragment;
    }


    @Override
    public Drawable getBackgroundForPage(int row, int column) {
        return super.getBackgroundForPage(row, column);
    }

    @Override
    public int getRowCount() {
        return PAGES.length;
    }

    @Override
    public int getColumnCount(int rowNum) {
        return PAGES[rowNum].length;
    }


}
