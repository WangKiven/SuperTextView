/*
 * Copyright (C) 2019 CoorChice <icechen_@outlook.com>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * <p>
 * Last modified 7/31/19 1:55 PM
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.coorchice.library.R;
import com.coorchice.library.SuperTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExSuperTextView extends SuperTextView {
    public ExSuperTextView(Context context) {
        super(context);
        init(null);
    }

    public ExSuperTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ExSuperTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public ExSuperTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    private List<Map<String, Object>> bgs = null;
    private int bgIndex = 0;

    private List<String> tcs = null;
    private int tcIndex = 0;
    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray =
                    getContext().obtainStyledAttributes(attrs, R.styleable.ExSuperTextView);

            // "{形状：矩形，左上圆角：5dp，右上圆角：10dp，填充颜色：红色，边框颜色：绿色，边框宽度：1dp}"
            // "{形状：圆形，填充颜色：红色，边框颜色：绿色，边框宽度：1dp}"
            // "{},{},{}"
            //
            // "{shape：rectangle，leftTopCorner：5dp，rightTopCorner：10dp, leftBottom: 3dp, rightBottom: 5dp，fillColor：#AF9898，rimColor：绿色，rimWidth：1dp}"
            // "{shape：circle，fillColor：@color/color_4，rimColor：#AF9898，rimWidth：1dp}"
            // shape: rectangle/circle 默认rectangle
            if (typedArray.hasValue(R.styleable.ExSuperTextView_estv_background)) {

//                String text = "{shape：circle，fillColor：@color/color_4，rimColor：#AF9898，rimWidth：1dp}" +
//                        ",{shape：rectangle，leftTopCorner：5dp，rightTopCorner：10dp，fillColor：#，rimColor：绿色，rimWidth：1dp}";

                String text = typedArray.getString(R.styleable.ExSuperTextView_estv_background);
                bgs = parseBg(text);
                if (bgs.size() == 0) {
                    bgs = null;
                } else {

                    if (typedArray.hasValue(R.styleable.ExSuperTextView_estv_background_index)) {
                        bgIndex = typedArray.getInt(R.styleable.ExSuperTextView_estv_background_index, 0);
                    }

                    updateBackground();
                }
            }

            if (typedArray.hasValue(R.styleable.ExSuperTextView_estv_textColor)) {
                String text = typedArray.getString(R.styleable.ExSuperTextView_estv_textColor);
                tcs = parseTextColor(text);
                if (tcs.size() == 0) {
                    tcs = null;
                } else {

                    if (typedArray.hasValue(R.styleable.ExSuperTextView_estv_textColor_index)) {
                        tcIndex = typedArray.getInt(R.styleable.ExSuperTextView_estv_textColor_index, 0);
                    }

                    updateTextColor();
                }
            }


            typedArray.recycle();
        }
    }

    public void setTextColorIndex(int tcIndex) {
        if (this.tcIndex != tcIndex) {
            this.tcIndex = tcIndex;
            updateTextColor();
        }
    }

    private void updateTextColor() {
        if (tcs == null) {
            return;
        }

        if (tcIndex < 0 || tcIndex > tcs.size() - 1) {
            return;
        }


    }

    public void setBgIndex(int bgIndex) {
        if (this.bgIndex != bgIndex) {
            this.bgIndex = bgIndex;
            updateBackground();
        }
    }

    private void updateBackground() {
        if (bgs == null) {
            return;
        }

        if (bgIndex < 0 || bgIndex > bgs.size() - 1) {
            return;
        }

        Map<String, Object> bgMap = bgs.get(bgIndex);

        for (String key : bgMap.keySet()) {
            switch (key) {
                case "shape":
                    break;
                case "fillColor":
                    break;
                case "rimColor":
                    break;
                case "rimWidth":
                    break;
                    // shape：rectangle 独有参数
                case "corner":
                    break;
                case "leftTopCorner":
                    break;
                case "rightTopCorner":
                    break;
                case "leftBottom":
                    break;
                case "rightBottom":
                    break;
                default:
                    break;
            }
        }
    }

    private List<Map<String, Object>> parseBg(String text) {
        int i = 0;

        List<Map<String, Object>> list = new ArrayList<>();
        Parse pp = new Parse();
        for (; i < text.length(); i++) {
            char c = text.charAt(i);
            switch (c) {
                case '{':
                    i = i + 1;
                    list.add(pp.start(text, i));
                    i = pp.i;
                    break;
                default:
                    break;
            }
        }

        return list;
    }

    private List<String> parseTextColor(String text) {
        List<String> tcs = new ArrayList<>();


        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            switch (c) {
                case '{':
                    break;
                case '}':
                case ' ':
                    break;
                case '：':
                case ':':
                    break;
                case '，':
                case ',':
                    if (sb.length() > 0) {
                        tcs.add(sb.toString());

                        sb = new StringBuilder();
                    }
                    break;
                default:
                    sb.append(c);
                    break;

            }
        }

        if (sb.length() > 0) {
            tcs.add(sb.toString());
        }

        return tcs;
    }


    class Parse {
        private int i = 1;

        Map<String, Object> start(String text, int start) {
            i = start;
            return parse(text);
        }

        private Map<String, Object> parse(String text) {
            Map<String, Object> map = new HashMap<>();
            String cacheKey = null;
            StringBuilder sb = new StringBuilder();
            for (; i < text.length(); i++) {
                char c = text.charAt(i);

                switch (c) {
                    case '{':
                        i++;
                        Map<String, Object> mm = parse(text);
                        if (cacheKey != null) {
                            map.put(cacheKey, mm);
                        }
                        sb = new StringBuilder();
                        break;
                    case '}':
                        if (cacheKey != null) {
                            map.put(cacheKey, sb.toString());
                        }
                        return map;
                    case ' ':
                        break;
                    case '：':
                    case ':':
                        cacheKey = sb.toString();
                        sb = new StringBuilder();
                        break;
                    case '，':
                    case ',':
                        if (cacheKey != null) {
                            map.put(cacheKey, sb.toString());
                        }
                        sb = new StringBuilder();
                        break;
                    default:
                        sb.append(c);
                        break;

                }
            }

            return map;
        }
    }
}
