package com.gj.test.base.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.gj.test.base.util.mvc.CustomTimestampEditor;


public class BaseController {

	@InitBinder
	public void initBinder( WebDataBinder binder ) {
		DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
		dateFormat.setLenient( true );
		SimpleDateFormat timestampFormat = new SimpleDateFormat( "yyyy-MM-dd  HH:mm:ss" );
		timestampFormat.setLenient( true );
		binder.registerCustomEditor( Timestamp.class, new CustomTimestampEditor( timestampFormat, true ) );
		binder.registerCustomEditor( Date.class, new CustomDateEditor( dateFormat, true ) );

	}

	@ModelAttribute
	public void initParam( Model model ) {
//		model.addAttribute( ProjectCodeBook.CAIBAN_URL, caibanUrl);
//		model.addAttribute( Constants.CAIBAN_DEALIER_MANAGE_URL, dealierManageUrl);
	}

	public Locale getLocale( HttpServletRequest request ) {
		Locale locale = ( Locale )request.getSession().getAttribute( "_LocaleInfo" );
		if( locale == null ) {
			locale = LocaleContextHolder.getLocale();
		}
		return locale;
	}


	public void reSetSubmitToken( HttpServletRequest request ) {
		request.getSession( false ).setAttribute( "submitToken", "" ); // 提交的token
	}
}
