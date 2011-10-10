<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
[
<s:iterator id="album" value="albums" status="status">
{name:'<s:property value="#album.name" />',id:'<s:property value="#album.id" />'}<s:if test="#status.last==false">,</s:if> 
</s:iterator>
] 