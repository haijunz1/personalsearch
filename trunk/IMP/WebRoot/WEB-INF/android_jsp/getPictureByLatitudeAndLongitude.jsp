<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
[
<s:iterator id="photo" value="photos" status="status">
{id:'<s:property value="#photo.id" />',tags:'<s:property value="#photo.tags"/>',
	path:'<s:property value="#photo.path"/>',latitude:'<s:property value="#photo.latitude"/>',
	longitude:'<s:property value="#photo.longitude"/>',source:'<s:property value="#photo.source"/>',
	name:'<s:property value="#photo.name"/>',ownerId:'<s:property value="#photo.ownerId"/>',
	uploadDate:'<s:property value="#photo.uploadDate"/>',ownerName:'<s:property value="#photo.ownerName"/>'}<s:if test="#status.last==false">,</s:if> 
</s:iterator>
] 