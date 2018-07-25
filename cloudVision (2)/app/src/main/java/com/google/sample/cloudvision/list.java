/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.sample.cloudvision;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;


/**
 * Created by yuhu on 11/28/16.
 */

public class list extends Activity {

    private ScavengeList list = new ScavengeList();


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);


        ScrollView sv = new ScrollView(this);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);

        TextView tv = new TextView(this);
        tv.setText("Scavenger List!");
        tv.setTextSize(40);


        ll.addView(tv);


//       list = new ScavengeList();


        Iterator<String> iterate = list.getNameList().iterator();


        while (iterate.hasNext()) {
            TextView cb = new TextView(this);
            cb.setText(iterate.next());
            ll.addView(cb);

        }

        TextView tvFound = new TextView(this);
        tvFound.setTextSize(40);
        tvFound.setText("Found object");
        ll.addView(tvFound);

        if (list.getScavengeList().isEmpty() == true) {
            //do nothing
        } else {
            Iterator<ScavengeObject> it = list.getScavengeList().iterator();

            while (it.hasNext()) {
                TextView a = new TextView(this);
                ScavengeObject next = it.next();
                a.setText(next.getName() + "\n " + next.getLocation());
                ll.addView(a);

            }
        }


        Button btn = new Button(this);
        btn.setText("Go");
        ll.addView(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(list.this, MainActivity.class);
                list.this.startActivity(mainActivity);
            }
        });

        setContentView(sv);
    }


}