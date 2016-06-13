package findgas.com.findgas.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import findgas.com.findgas.R;

/**
 * Created by akshaymanathkar on 31/05/16.
 */
public class TextWithSubTextFragment extends Fragment {
    private final static String KEY_MAIN_TEXT = "keyMainText";
    private final static String KEY_SUB_TEXT = "keySubText";

    private TextView subTextView;

    public static TextWithSubTextFragment newInstance(String mainText, String subText) {
        TextWithSubTextFragment textWithSubTextFragment =
                new TextWithSubTextFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_MAIN_TEXT, mainText);
        bundle.putString(KEY_SUB_TEXT, subText);
        textWithSubTextFragment.setArguments(bundle);
        return textWithSubTextFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_text_with_sub_text, container, false);

        TextView mainTextView = (TextView) view.findViewById(
                R.id.main_text_view);
        mainTextView.setText(getArguments().getString(KEY_MAIN_TEXT));

        subTextView = (TextView) view.findViewById(
                R.id.sub_text_view);
        subTextView.setText(getArguments().getString(KEY_SUB_TEXT));

        return view;
    }
}
