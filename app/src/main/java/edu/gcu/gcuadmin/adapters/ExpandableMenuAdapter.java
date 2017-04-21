package edu.gcu.gcuadmin.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import edu.gcu.gcuadmin.R;
import edu.gcu.gcuadmin.models.ExpandableMenuModel;
/**
 * Created by Shrivats on 4/5/2017.
 */

public class ExpandableMenuAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<ExpandableMenuModel> mListDataHeader;
    private HashMap<ExpandableMenuModel, List<String>> mListDataChild;
    private ExpandableListView mExpandList;

    public ExpandableMenuAdapter(Context mContext, List<ExpandableMenuModel> mListDataHeader, HashMap<ExpandableMenuModel, List<String>> mListDataChild, ExpandableListView mExpandList) {
        this.mContext = mContext;
        this.mListDataHeader = mListDataHeader;
        this.mListDataChild = mListDataChild;
        this.mExpandList = mExpandList;
    }

    @Override
    public int getGroupCount() {
        return this.mListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int childCount = 0;

        if(groupPosition != 3)
            childCount = this.mListDataChild.get(this.mListDataHeader.get(groupPosition)).size();

        return childCount;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mListDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.mListDataChild.get(this.mListDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ExpandableMenuModel headerTitle = (ExpandableMenuModel) getGroup(groupPosition);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.menu_header, null);
        }
        TextView lblHeader = (TextView) convertView.findViewById(R.id.submenu);
        ImageView lblIcon = (ImageView) convertView.findViewById(R.id.menu_icon);
        lblHeader.setTypeface(null, Typeface.BOLD);
        lblHeader.setText(headerTitle.getIconName());
        lblIcon.setImageResource(headerTitle.getIconImg());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.menu_sub_menu, null);
        }
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.sub_menu);

        txtListChild.setText(childText);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
