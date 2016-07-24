package com.gj.test.base.vo;

import java.util.List;

public class MesJson {

    private String code = "0";
    private String mesTitle = "";
    private String msg = "";
    private int nId = 0;
    private String sId = "";
    private long lId = 0;
    private Object object;
    private int arg = 0;
    private int arg2 = 0;
    private Double arg3 = 0.0;

    private List objectList;

    private List objectList1;

    public enum MesJsonConstant {
        code_s("0"), code_f("-1"), msg_s("保存成功!"), msg_f("保存失败!"), msg_delete_f("删除失败!"), msg_delete_s("删除成功!");
        private String msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg( String msg ) {
            this.msg = msg;
        }

        MesJsonConstant( String msg ) {
            this.msg = msg;
        }
    }

    public MesJson() {
        this.code = MesJsonConstant.code_s.getMsg();
        this.msg = MesJsonConstant.msg_s.getMsg();
    }

    public MesJson( String code, String msg ) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public int getnId() {
        return nId;
    }

    public void setnId( int nId ) {
        this.nId = nId;
    }

    public String getsId() {
        return sId;
    }

    public void setsId( String sId ) {
        this.sId = sId;
    }

    public long getlId() {
        return lId;
    }

    public void setlId( long lId ) {
        this.lId = lId;
    }

    public Object getObject() {
        return object;
    }

    public void setObject( Object object ) {
        this.object = object;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    public String getMesTitle() {
        return mesTitle;
    }

    public void setMesTitle( String mesTitle ) {
        this.mesTitle = mesTitle;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg( String msg ) {
        this.msg = msg;
    }

    public int getArg() {
        return arg;
    }

    public void setArg( int arg ) {
        this.arg = arg;
    }

    public int getArg2() {
        return arg2;
    }

    public void setArg2( int arg2 ) {
        this.arg2 = arg2;
    }

    public List getObjectList() {
        return objectList;
    }

    public void setObjectList( List objectList ) {
        this.objectList = objectList;
    }

    public Double getArg3() {
        return arg3;
    }

    public void setArg3( Double arg3 ) {
        this.arg3 = arg3;
    }

    public List getObjectList1() {
        return objectList1;
    }

    public void setObjectList1( List objectList1 ) {
        this.objectList1 = objectList1;
    }

}
