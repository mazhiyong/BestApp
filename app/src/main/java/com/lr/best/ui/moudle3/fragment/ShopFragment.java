package com.lr.best.ui.moudle3.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.lr.best.R;
import com.lr.best.basic.BasicFragment;
import com.lr.best.utils.tool.UtilTools;

import java.util.Map;

import butterknife.BindView;

public class ShopFragment extends BasicFragment {

    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.back_text)
    TextView backText;
    @BindView(R.id.left_back_lay)
    LinearLayout leftBackLay;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.right_text_tv)
    TextView rightTextTv;
    @BindView(R.id.right_img)
    ImageView rightImg;
    @BindView(R.id.right_lay)
    LinearLayout rightLay;
    @BindView(R.id.divide_line)
    View divideLine;
    @BindView(R.id.top_layout)
    LinearLayout titleBarView;
    @BindView(R.id.iv)
    ImageView iv;

    public ShopFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shop;
    }

    @Override
    public void init() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) getActivity().getResources().getDimension(R.dimen.title_item_height) + UtilTools.getStatusHeight2(getActivity()));
        titleBarView.setLayoutParams(layoutParams);
        titleBarView.setPadding(0, UtilTools.getStatusHeight2(getActivity()), 0, 0);

        backText.setVisibility(View.GONE);
        backImg.setVisibility(View.GONE);
        setBarTextColor();

        titleText.setText("商城");
        titleText.setCompoundDrawables(null,null,null,null);

       // GlideApp.with(getActivity()).load(R.drawable.shop).into(iv);
    }

    public void setBarTextColor() {
        StatusBarUtil.setLightMode(getActivity());
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void disimissProgress() {

    }

    @Override
    public void loadDataSuccess(Map<String, Object> tData, String mType) {

    }

    @Override
    public void loadDataError(Map<String, Object> map, String mType) {

    }
}


