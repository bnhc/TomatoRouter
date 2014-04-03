package com.somethingprofane.tomato;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.somethingprofane.db.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class NewGroupActivity extends ActionBarActivity {

    @InjectView(R.id.activity_new_group_saveGroupButton)
    Button saveButton;
    @InjectView(R.id.activity_new_group_deleteGroupButton)
    Button deleteButton;

    private EditText groupName;
    private TextView groupNameTxtView;
    private DeviceGroup deviceGroup;
    private ListView deviceListView;
    private List<Device> deviceList;

    private CheckBox enabledCheckbox;
    private CheckBox sunCheckBox;
    private CheckBox monCheckBox;
    private CheckBox tueCheckBox;
    private CheckBox wedCheckBox;
    private CheckBox thuCheckBox;
    private CheckBox friCheckBox;
    private CheckBox satCheckBox;
    private EditText ruleDescEditTxt;
    private Spinner beginSpinner;
    private Spinner finishSpinner;

    private Rule rule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        groupName = (EditText) findViewById(R.id.activity_new_group_groupNameTxt);
        groupNameTxtView = (TextView) findViewById(R.id.activity_new_group_groupNameLabel);
        deviceListView = (ListView) findViewById(R.id.activity_new_group_deviceList);

        enabledCheckbox = (CheckBox) findViewById(R.id.activity_group_new_enabledChkBox);
        ruleDescEditTxt = (EditText) findViewById(R.id.activity_group_new_descTxtView);
        sunCheckBox = (CheckBox) findViewById(R.id.activity_group_new_sunCheckBox);
        monCheckBox = (CheckBox) findViewById(R.id.activity_group_new_monCheckBox);
        tueCheckBox = (CheckBox) findViewById(R.id.activity_group_new_tueCheckBox);
        wedCheckBox = (CheckBox) findViewById(R.id.activity_group_new_wedCheckBox);
        thuCheckBox = (CheckBox) findViewById(R.id.activity_group_new_thuCheckBox);
        friCheckBox = (CheckBox) findViewById(R.id.activity_group_new_friCheckBox);
        satCheckBox = (CheckBox) findViewById(R.id.activity_group_new_satCheckBox);
        beginSpinner = (Spinner) findViewById(R.id.activity_group_new_beginTimeSpinner);
        finishSpinner = (Spinner) findViewById(R.id.activity_group_new_endTimeSpinner);

        ButterKnife.inject(this);

        deleteButton.setVisibility(View.INVISIBLE);

        setupSpinners();
        setupDeviceGroup();
        setupRule();
        setupDeviceListView(deviceListView);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDeviceGroup() {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle && bundle.containsKey("deviceGroupID")) {
            int deviceGroupId = bundle.getInt("deviceGroupID");
            deviceGroup = DatabaseManager.getInstance().getDeviceGroupWithId(deviceGroupId);
            groupName.setText(deviceGroup.getGroupName());
            deleteButton.setVisibility(View.VISIBLE);
            groupNameTxtView.setText("Group Name:");
        }
    }

    private void setupRule(){
        Bundle bundle = getIntent().getExtras();
        if (null != bundle && bundle.containsKey("deviceGroupID")) {
            rule = deviceGroup.getRule();
            if(rule.isEnabled()){
                enabledCheckbox.setChecked(true);
            }
            if(rule.getDescription()!=null){
                ruleDescEditTxt.setText(rule.getDescription());
            }
            if(rule.isSunday()){
                sunCheckBox.setChecked(true);
            }
            if(rule.isMonday()){
                monCheckBox.setChecked(true);
            }
            if(rule.isTuesday()){
                tueCheckBox.setChecked(true);
            }
            if(rule.isWednesday()){
                wedCheckBox.setChecked(true);
            }
            if(rule.isThursday()){
                thuCheckBox.setChecked(true);
            }
            if(rule.isFriday()){
                friCheckBox.setChecked(true);
            }
            if(rule.isSaturday()){
                satCheckBox.setChecked(true);
            }
            beginSpinner.setSelection(rule.getStartTime()/15);

            finishSpinner.setSelection(rule.getFinishTime()/15);

        }
    }

    private void setupSpinners() {
        Spinner beginSpinner = (Spinner) findViewById(R.id.activity_group_new_beginTimeSpinner);
        ArrayAdapter<CharSequence> beginAdapter = ArrayAdapter.createFromResource(this,
                R.array.time_array, android.R.layout.simple_spinner_item);
        beginAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        beginSpinner.setAdapter(beginAdapter);
        beginSpinner.setSelection(beginAdapter.getPosition("10:00 PM"));
        Spinner endSpinner = (Spinner) findViewById(R.id.activity_group_new_endTimeSpinner);
        ArrayAdapter<CharSequence> endAdapter = ArrayAdapter.createFromResource(this,
                R.array.time_array, android.R.layout.simple_spinner_item);
        endAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endSpinner.setAdapter(endAdapter);
        endSpinner.setSelection(endAdapter.getPosition("5:00 AM"));
    }

    private void setupDeviceListView(ListView deviceListView) {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle && bundle.containsKey("deviceGroupID")) {
            List<Device> devicesInNoGroupList = DatabaseManager.getInstance().getNonGroupDevices();
            deviceList = DatabaseManager.getInstance().getDevicesByGroup(deviceGroup);
            deviceList.addAll(devicesInNoGroupList);
        } else {
            deviceList = DatabaseManager.getInstance().getNonGroupDevices();
        }
        if (null != deviceList) {

            List<String> deviceNames = new ArrayList<String>();
            for (Device device : deviceList) {

                deviceNames.add(device.getDeviceName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, deviceNames);
            deviceListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            deviceListView.setAdapter(adapter);

            if (null != bundle && bundle.containsKey("deviceGroupID")) {
                for (int i = 0; i < deviceList.size(); i++) {
                    if (deviceList.get(i).getDeviceGroup() != null &&
                            deviceList.get(i).getDeviceGroup().getId() == bundle.getInt("deviceGroupID"))
                    {
                        deviceListView.setItemChecked(i, true);
                    }
                }
            }
        }
    }

    @OnClick(R.id.activity_new_group_saveGroupButton)
    public void onSaveButtonClick(Button saveButton) {

        List<Rule> ruleList;

        //Save and update group
        String name = groupName.getText().toString();
        if (null != name && name.length() > 0) {
            if (null != deviceGroup) {
                updateDeviceGroup(name);
            } else {
                createDeviceGroup(name);
            }
        }


        //Save and update devices to group
        SparseBooleanArray checked = deviceListView.getCheckedItemPositions();

        for (int i = 0; i < checked.size(); i++) {
            // Add device to selectedDevices if it is checked i.e.) == TRUE!
            int position = checked.keyAt(i);

            if (checked.valueAt(i)) {

                deviceList.get(position).setDeviceGroup(deviceGroup);
                System.out.println("Device "+deviceList.get(position).deviceName+" is checked");
            }
            else{
                deviceList.get(position).setDeviceGroup(null);
                System.out.println("Device "+deviceList.get(position).deviceName+" is not checked");
            }
            System.out.println("Device "+deviceList.get(position).getDeviceName());
            updateDevice(deviceList.get(position));
        }

       Bundle bundle = getIntent().getExtras();
        if (null != bundle && bundle.containsKey("deviceGroupID")) {

        }else{rule = new Rule();}

        //Save and update rules
        if (enabledCheckbox.isChecked()){
            rule.setEnabled(true);
        }else rule.setEnabled(false);

        if(ruleDescEditTxt.getText().toString()!=null){
            rule.setDescription(ruleDescEditTxt.getText().toString());
        }

        if (sunCheckBox.isChecked()){
            rule.setSunday(true);
        }else rule.setSunday(false);

        if (monCheckBox.isChecked()){
            rule.setMonday(true);
        }else rule.setMonday(false);

        if (tueCheckBox.isChecked()){
            rule.setTuesday(true);
        }else rule.setTuesday(false);

        if (wedCheckBox.isChecked()){
            rule.setWednesday(true);
        }else rule.setWednesday(false);

        if (thuCheckBox.isChecked()){
            rule.setThursday(true);
        }else rule.setThursday(false);

        if (friCheckBox.isChecked()){
            rule.setFriday(true);
        }else rule.setFriday(false);

        if (satCheckBox.isChecked()){
            rule.setSaturday(true);
        }else rule.setSaturday(false);

        rule.setStartTime(beginSpinner.getSelectedItemPosition()*15);

        rule.setFinishTime(finishSpinner.getSelectedItemPosition()*15);

        if (rule.getTomatoRuleId()==0){


        }


        if (null != bundle && bundle.containsKey("deviceGroupID")) {
            updateRule(rule);
        }else createRule(rule);

        deviceGroup.setRule(rule);
        DatabaseManager.getInstance().updateDeviceGroup(deviceGroup);


        finish();
    }

    @OnClick(R.id.activity_new_group_deleteGroupButton)
    public void onDeleteButtonClick(Button deleteButton) {
        if (null != deviceGroup) {
            deleteDeviceGroup(deviceGroup);
            finish();
        }
    }

    private void deleteDeviceGroup(DeviceGroup dg){
        List<Device> devicesToDelete = DatabaseManager.getInstance().getDevicesByGroup(dg);
        for (Device device:devicesToDelete){
            device.setDeviceGroup(null);
            updateDevice(device);
        }
        if (deviceGroup.getRule()!=null){
            DatabaseManager.getInstance().deleteRule(deviceGroup.getRule());
        }
        DatabaseManager.getInstance().deleteDeviceGroup(deviceGroup);

    }

    private void updateDeviceGroup(String name) {
        if (null != deviceGroup) {
            deviceGroup.setGroupName(name);
            DatabaseManager.getInstance().updateDeviceGroup(deviceGroup);
        }
    }

    private void createDeviceGroup(String name) {
        DeviceGroup dg = new DeviceGroup();
        dg.setGroupName(name);
        DatabaseManager.getInstance().addDeviceGroup(dg);
        deviceGroup = dg;
    }

    private void updateDevice(Device device) {
        if (device.getDeviceGroup()!=null){
            System.out.println("Device "+device.getDeviceName()+" is being added to group "+device.getDeviceGroup().getGroupName());
        }
        else{
            System.out.println("Device "+device.getDeviceName()+" DeviceGroup is being set to null");
        }
        DatabaseManager.getInstance().updateDevice(device);
    }

    private void createRule(Rule rule){
        DatabaseManager.getInstance().addRule(rule);
    }

    private void updateRule(Rule rule){
        DatabaseManager.getInstance().updateRule(rule);
    }

}
