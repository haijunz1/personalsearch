<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
[
<s:iterator id="photo" value="photos" status="status">
{id:'<s:property value="#photo.id" />',tags:'<s:property value="#photo.tags" />',
path:'<s:property value="#photo.path" />'}
		<s:if test="#status.last==false">
		,
	   </s:if> 
</s:iterator>
] 