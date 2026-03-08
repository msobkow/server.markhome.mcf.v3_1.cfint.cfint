// Description: Java 25 Object interface for CFInt Service.

/*
 *	server.markhome.mcf.CFInt
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal 3.1 CFInt - Internet Essentials
 *	
 *	This file is part of Mark's Code Fractal CFInt.
 *	
 *	Mark's Code Fractal CFInt is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU Library General Public License,
 *	Version 3 or later.
 *	
 *	Mark's Code Fractal CFInt is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU Library General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	Mark's Code Fractal CFInt is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU Library General Public License
 *	along with Mark's Code Fractal CFInt.  If not, see <https://www.gnu.org/licenses/>.
 *	
 *	If you wish to modify and use this code without publishing your changes in order to
 *	tie it to proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
 *	
 */

package server.markhome.mcf.v3_1.cfint.cfintobj;

import java.math.*;
import java.sql.*;
import java.text.*;
import java.time.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfsec.cfsecobj.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;

public interface ICFIntServiceObj
	extends ICFSecServiceObj
{
	/**
	 *	Initially, the class code for an object is ICFSecService.CLASS_CODE, but the Obj layer relies on class code translation to map those
	 *	backing store entities to a runtime set of front-facing classcodes that the clients download and use when talking to the server implementing this code base.
	 *
	 *	@return The runtime class code used by this object. Only after the system is fully booted are these values stable and reliable.
	 */
	int getClassCode();
	/**
	 *	Get the user who created this instance.
	 *
	 *	@return	The ICFSecSecUserObj instance who created this instance.
	 */
	ICFSecSecUserObj getCreatedBy();

	/**
	 *	Get the LocalDateTime this instance was created.
	 *
	 *	@return	The LocalDateTime value for the creation time of the instance.
	 */
	LocalDateTime getCreatedAt();

	/**
	 *	Get the user who updated this instance.
	 *
	 *	@return	The ICFSecSecUserObj instance who updated this instance.
	 */
	ICFSecSecUserObj getUpdatedBy();

	/**
	 *	Get the LocalDateTime date-time this instance was updated.
	 *
	 *	@return	The LocalDateTime value for the create time of the instance.
	 */
	LocalDateTime getUpdatedAt();
	/**
	 *	Realise this instance of a Service.
	 *
	 *	@return	CFSecServiceObj instance which should be subsequently referenced.
	 */
	ICFSecServiceObj realise();

	/**
	 *	Forget this instance from the cache.
	 *	<p>
	 *	This method should only be invoked by implementation internals.
	 */
	void forget();

	/**
	 *	Re-read this instance by it's primary key.
	 *
	 *	@return	ICFSecServiceObj the reference to the cached or read (realised) instance.
	 */
	ICFSecServiceObj read();

	/**
	 *	Re-read this instance by it's primary key.
	 *
	 *	@return	ICFSecServiceObj the reference to the cached or read (realised) instance.
	 */
	ICFSecServiceObj read( boolean forceRead );

	/**
	 *	Initialize and return a locked edition of this Service instance.
	 *
	 *	@return	The newly locked ICFSecServiceEditObj edition of this instance.
	 */
	ICFSecServiceEditObj beginEdit();

	/**
	 *	End this edition of this Service instance.
	 *
	 *	@throws	CFLibNotSupportedException if you try to end a read-only view.
	 */
	void endEdit();

	/**
	 *	Get the current edition of this Service instance.
	 *
	 *	@return	The ICFSecServiceEditObj edition of this instance.
	 */
	ICFSecServiceEditObj getEdit();

	/**
	 *	Get the current edition of this Service instance as a ICFSecServiceEditObj.
	 *
	 *	@return	The ICFSecServiceEditObj edition of this instance.
	 */
	ICFSecServiceEditObj getEditAsService();

	/**
	 *	Get the ICFSecServiceTableObj table cache which manages this instance.
	 *
	 *	@return	ICFSecServiceTableObj table cache which manages this instance.
	 */
	ICFSecServiceTableObj getServiceTable();

	/**
	 *	Get the ICFSecSchemaObj schema cache which manages this instance.
	 *
	 *	@return	ICFSecSchemaObj schema cache which manages this instance.
	 */
	ICFSecSchemaObj getSchema();

	/**
	 *	Set the ICFSecSchemaObj schema cache which manages this instance.
	 *	Should only be used to install overloads of the buff implementation wired specifically to a transport implementation
	 *	that eventually hits a server running a JPA backend.
	 *
	 *	@param schema	ICFSecSchemaObj schema cache which manages this instance.
	 */
	void setSchema(ICFSecSchemaObj schema);

	/**
	 *	Get the ICFSecService instance which currently backs this instance.
	 *	<p>
	 *	This value <i>will</i> change for read-only instances, so you should
	 *	not hold on to the value as a reference anywhere outside the current call stack.
	 *
	 *	@return	ICFSecService instance which currently backs this object.
	 */
	ICFSecService getRec();

	/**
	 *	Internal use only.
	 */
	void setRec( ICFSecService value );

	/**
	 *	Get the ICFSecService instance which currently backs this instance.
	 *	<p>
	 *	This value <i>will</i> change for read-only instances, so you should
	 *	not hold on to the value as a reference anywhere outside the current call stack.
	 *
	 *	@return	ICFSecService instance which currently backs this object.
	 */
	ICFSecService getServiceRec();

	/**
	 *	Is this a new instance?
	 *
	 *	@return	True if this is a new instance, otherwise false if it has
	 *		been read, locked, or created.
	 */
	boolean getIsNew();

	/**
	 *	Indicate whether this is a new instance.
	 *	<p>
	 *	This method should only be used by implementation internals.
	 *
	 *	@param	True if this is a new instance, otherwise false.
	 */
	void setIsNew( boolean value );

	/**
	 *	Get the required ICFIntClusterObj instance referenced by the Cluster key.
	 *
	 *	@return	The required ICFIntClusterObj instance referenced by the Cluster key.
	 */
	ICFSecClusterObj getRequiredOwnerCluster();

	/**
	 *	Get the required ICFSecClusterObj instance referenced by the Cluster key.
	 *
	 *	@return	The required ICFSecClusterObj instance referenced by the Cluster key.
	 */
	ICFSecClusterObj getRequiredOwnerCluster( boolean forceRead );

	/**
	 *	Get the optional ICFIntHostNodeObj instance referenced by the Host key.
	 *
	 *	@return	The optional ICFIntHostNodeObj instance referenced by the Host key.
	 */
	ICFSecHostNodeObj getOptionalContainerHost();

	/**
	 *	Get the optional ICFSecHostNodeObj instance referenced by the Host key.
	 *
	 *	@return	The optional ICFSecHostNodeObj instance referenced by the Host key.
	 */
	ICFSecHostNodeObj getOptionalContainerHost( boolean forceRead );

	/**
	 *	Get the optional ICFIntServiceTypeObj instance referenced by the ServiceType key.
	 *
	 *	@return	The optional ICFIntServiceTypeObj instance referenced by the ServiceType key.
	 */
	ICFSecServiceTypeObj getOptionalParentServiceType();

	/**
	 *	Get the optional ICFSecServiceTypeObj instance referenced by the ServiceType key.
	 *
	 *	@return	The optional ICFSecServiceTypeObj instance referenced by the ServiceType key.
	 */
	ICFSecServiceTypeObj getOptionalParentServiceType( boolean forceRead );

	/**
	 *	Get the required CFLibDbKeyHash256 attribute ServiceId.
	 *
	 *	@return	The required CFLibDbKeyHash256 attribute ServiceId.
	 */
	CFLibDbKeyHash256 getRequiredServiceId();

	/**
	 *	Get the required CFLibDbKeyHash256 attribute ClusterId.
	 *
	 *	@return	The required CFLibDbKeyHash256 attribute ClusterId.
	 */
	CFLibDbKeyHash256 getRequiredClusterId();

	/**
	 *	Get the required CFLibDbKeyHash256 attribute HostNodeId.
	 *
	 *	@return	The required CFLibDbKeyHash256 attribute HostNodeId.
	 */
	CFLibDbKeyHash256 getRequiredHostNodeId();

	/**
	 *	Get the required CFLibDbKeyHash256 attribute ServiceTypeId.
	 *
	 *	@return	The required CFLibDbKeyHash256 attribute ServiceTypeId.
	 */
	CFLibDbKeyHash256 getRequiredServiceTypeId();

	/**
	 *	Get the required short attribute HostPort.
	 *
	 *	@return	The required short attribute HostPort.
	 */
	short getRequiredHostPort();

	/**
	 *	Internal use only.
	 */
	void copyPKeyToRec();

	/**
	 *	Internal use only.
	 */
	void copyRecToPKey();

}
