package layout;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cheetatech.com.colorhub.ColorPickerActivity;
import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.defines.BoardEditor;
import cheetatech.com.colorhub.defines.ColorItem;
import cheetatech.com.colorhub.view.ColorPanelView;
import cheetatech.com.colorhub.view.ColorPickerView;

public class ColorPicker3 extends Fragment implements ColorPickerView.OnColorChangedListener {


    @BindView(R.id.colorView)
    View colorView;

    @BindView(R.id.color)
    TextView colorTextView;

    private int colorText = 0;
    @BindView(R.id.colorpickerview__color_picker_view1)
    ColorPickerView mColorPickerView;
    //private ColorPickerView mColorPickerView;

    private ColorPicker1.OnColorListener mListener = null;

    public ColorPicker3() {
    }

    public static ColorPicker3 newInstance(String param1, String param2) {
        ColorPicker3 fragment = new ColorPicker3();
        return fragment;
    }

    public static ColorPicker3 newInstance(ColorPicker1.OnColorListener listener) {
        ColorPicker3 fragment = new ColorPicker3();
        fragment.setListener(listener);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_color_picker3, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        colorView = (View) getView().findViewById(R.id.colorView);
//        colorTextView = (TextView)getView().findViewById(R.id.color);

//        mColorPickerView = (ColorPickerView) getView().findViewById(R.id.colorpickerview__color_picker_view1);

        final int initialColor = 0xFFE12109;;
        mColorPickerView.setOnColorChangedListener(this);
        mColorPickerView.setColor(initialColor, true);
        colorView.setBackgroundColor(initialColor);

        colorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BoardEditor.getInstance().copyToClipBoard(colorTextView.getText().toString());
                Toast.makeText(BoardEditor.getInstance().getContext(), "Color " + colorTextView.getText().toString() +
                        " copied to clipboard...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.add_button) void addColorButtonClick(){
        String color = colorTextView.getText().toString();
        if(mListener != null)
            this.mListener.onAddColor(color);
    }

    private String inverseColor(String hexColor)
    {
        String ff = "FF";
        String r = hexColor.substring(2,4);
        String g = hexColor.substring(4,6);
        String b = hexColor.substring(6,8);


        BigInteger ri = new BigInteger(r,16);
        BigInteger gi = new BigInteger(g,16);
        BigInteger bi = new BigInteger(b,16);


        BigInteger fi = new BigInteger(ff,16);

        BigInteger rs = ri.xor(fi);
        BigInteger gs = gi.xor(fi);
        BigInteger bs = bi.xor(fi);

        String res = String.format("#%02x%02x%02x",rs,gs,bs);
        return res;
    }

    @Override
    public void onColorChanged(int newColor) {

        colorText = newColor;
        colorTextView.setText("#" + Integer.toHexString(newColor));
        colorTextView.setTextColor(Color.parseColor(inverseColor(Integer.toHexString(newColor))));
        colorView.setBackgroundColor(colorText);
    }

    public void setListener(ColorPicker1.OnColorListener listener) {
        this.mListener = listener;
    }
}
