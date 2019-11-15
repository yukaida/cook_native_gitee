package com.ebanswers.kitchendiary.constant;

/**
 * 作者：Administrator on 2018/5/8 17:47
 */

public class AndroidForJs {
    private static String TAG = "AndroidForJs";

/*
    private Context context;
    private ArrayList<CourtUser> courtUsers = new ArrayList<>();
    private ArrayList<CoOffice> coOffices = new ArrayList<>();
    private ArrayList<AddressInfo> addressInfoList = new ArrayList<>();
    private PersonAdapter personAdapter;
    private ArrayList<Decument> decuments = new ArrayList<>();
    private ArrayList<Decument> decumentlist = new ArrayList<>();
    WebView webView;
    public AndroidForJs(Context context, WebView webView) {
        this.context = context;
        this.webView=webView;
    }

    //api17以后，只有public且添加了 @JavascriptInterface 注解的方法才能被调用
    @JavascriptInterface
    public void openNewWeb(String toast) {
        Intent intent = new Intent(context, WebViewDetailActivity.class);
        intent.putExtra("url", toast);
        context.startActivity(intent);
    }


    @JavascriptInterface
    public void toBack1() {

    }

    @JavascriptInterface
    public void logout() {
        BusEvent be = new BusEvent(BusEvent.EVENT_APPSCT_INVALID, "当前登录被挤下线，点击确定将跳转登录页面重新登录");
        EventBusUtil.sendStickyEvent(be);
    }

    @JavascriptInterface
    public String getCurrenUser() {
        String json = (String) SPUtils.get("json", "");
        return json;
    }

    @JavascriptInterface
    public String datePicker(String a,final String b) {
        MeetingTimePopupWindow meetingReceptionFragment = new MeetingTimePopupWindow(context);
        meetingReceptionFragment.showPopupWindow(webView);
        meetingReceptionFragment.setOnselectTimelistener(new MeetingTimePopupWindow.OnselectTimelistener() {
            @Override
            public void selectDate(final String date, final String time) {
                //long stringToDate = getStringToDate(date + time, "yyyy-MM-dd HH:mm");
                // SimpleDateFormat  sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
               // tvVideotime.setText(date + time);
                // 传递参数调用
                webView.post(new Runnable() {
                    @Override
                    public void run() {

                        // 注意调用的JS方法名要对应上
                        // 调用javascript的callJS()方法
                        webView.loadUrl("javascript:"+b+"('"+date+" "+time+"')");
                    }
                });



            }
        });
        return "";

    }
    @JavascriptInterface
    public String fileUpload(String a,final String b) {
          new PhotoPopupWindow(context, webView);
        return "";

    }
    @JavascriptInterface
    public String selectUser(String a,final String b) {
        LoadDeptUser();
        return "";

    }
    @JavascriptInterface
    public String niceSelect(String a,final String b) {
        String json=new String(a);
        try {
           // JSONObject jsonObject=new JSONObject(json);
            //JSONArray jsonArray=new JSONArray();
            final List<JsonBean> jsonBean=new ArrayList<JsonBean>();
            Gson gson=new Gson();
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (final JsonElement elem : array) {
                jsonBean.add(gson.fromJson(elem, JsonBean.class));
            }

            List<String> persons = new ArrayList<>();
            for (int i = 0; i < jsonBean.size(); i++) {
                persons.add(jsonBean.get(i).getText());
            }

            if (persons.size() > 0) {
                PersonPopupWindow personPopupWindow = new PersonPopupWindow(context, persons);
                personPopupWindow.showPopupWindow(webView);
                personPopupWindow.setSelectLeaveListener(new PersonPopupWindow.SelectLeaveListener() {
                    @Override
                    public void selectUser(final  int typeId, final String typeName) {
                        webView.post(new Runnable() {
                            @Override
                            public void run() {

                                // 注意调用的JS方法名要对应上
                                // 调用javascript的callJS()方法
                                Map<String,String> map=new HashMap<String,String>();
                                map.put("code",jsonBean.get(typeId).getCode());
                                map.put("text",typeName);

                                String json= JSON.toJSONString(map);
                                webView.loadUrl("javascript:"+b+"('"+json+"')");
                            }
                        });

                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";

    }

    private void LoadDeptUser() {
        decuments.clear();
        List<Decument> decumentList = DataSupport.findAll(Decument.class);
        List<List<AddressInfo>> lists = new ArrayList<List<AddressInfo>>();
        LogUtils.i("查询内容：" + DataSupport.findAll(Decument.class));
        if (decumentList != null && decumentList.size() > 0) {
            decuments.addAll(decumentList);
        }

        for (int i = 0; i < decuments.size(); i++) {
            Decument decument = decuments.get(i);
            List<AddressInfo> addressInfos = DataSupport.where("parentId = ?", decument.getPkId() + "").find(AddressInfo.class);
            LogUtils.i("查询长度：" + addressInfos.size());
            lists.add(addressInfos);
        }

        MeetingDeptUserPopupWindow meetingDeptUserPopupWindow = new MeetingDeptUserPopupWindow(context, decumentList, lists, addressInfoList);
        meetingDeptUserPopupWindow.showPopupWindow(webView);
        meetingDeptUserPopupWindow.setSelectLeaveListener(new MeetingDeptUserPopupWindow.SelectLeaveListener() {
            @Override
            public void selectType(List<AddressInfo> addressInfos) {
                LogUtils.i("长度：" + addressInfos.size());
                if (addressInfos.size() > 0) {
                    addressInfoList = new ArrayList<>();
                    courtUsers.clear();
//                    addressInfoListTemp.clear();
                    addressInfoList.addAll(addressInfos);
//                    addressInfoListTemp.addAll(addressInfos);
               //     meetingThisCountPeopleRv.setVisibility(View.VISIBLE);
                    personAdapter.setAddressInfos(addressInfoList);
                    personAdapter.notifyDataSetChanged();

                    for (int i = 0; i < addressInfos.size(); i++) {
                        CourtUser courtUser = new CourtUser();
                        courtUser.setDeptId(addressInfos.get(i).getParentId());
                        courtUser.setUserItcode(addressInfos.get(i).getTextName());
                        courtUsers.add(courtUser);
                    }
                }

            }

            @Override
            public void cancle() {

            }
        });

    }
*/


}
