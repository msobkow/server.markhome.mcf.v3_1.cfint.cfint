// Description: Java 25 Object interface for CFInt HostNode.

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

public interface ICFIntHostNodeObj
	extends ICFSecHostNodeObj
{
	/**
	 *	Initially, the class code for an object is ICFSecHostNode.CLASS_CODE, but the Obj layer relies on class code translation to map those
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
	 *	Realise this instance of a HostNode.
	 *
	 *	@return	CFSecHostNodeObj instance which should be subsequently referenced.
	 */
	ICFSecHostNodeObj realise();

	/**
	 *	Forget this instance from the cache.
	 *	<p>
	 *	This method should only be invoked by implementation internals.
	 */
	void forget();

	/**
	 *	Re-read this instance by it's primary key.
	 *
	 *	@return	ICFSecHostNodeObj the reference to the cached or read (realised) instance.
	 */
	ICFSecHostNodeObj read();

	/**
	 *	Re-read this instance by it's primary key.
	 *
	 *	@return	ICFSecHostNodeObj the reference to the cached or read (realised) instance.
	 */
	ICFSecHostNodeObj read( boolean forceRead );

	/**
	 *	Initialize and return a locked edition of this HostNode instance.
	 *
	 *	@return	The newly locked ICFSecHostNodeEditObj edition of this instance.
	 */
	ICFSecHostNodeEditObj beginEdit();

	/**
	 *	End this edition of this HostNode instance.
	 *
	 *	@throws	CFLibNotSupportedException if you try to end a read-only view.
	 */
	void endEdit();

	/**
	 *	Get the current edition of this HostNode instance.
	 *
	 *	@return	The ICFSecHostNodeEditObj edition of this instance.
	 */
	ICFSecHostNodeEditObj getEdit();

	/**
	 *	Get the current edition of this HostNode instance as a ICFSecHostNodeEditObj.
	 *
	 *	@return	The ICFSecHostNodeEditObj edition of this instance.
	 */
	ICFSecHostNodeEditObj getEditAsHostNode();

	/**
	 *	Get the ICFSecHostNodeTableObj table cache which manages this instance.
	 *
	 *	@return	ICFSecHostNodeTableObj table cache which manages this instance.
	 */
	ICFSecHostNodeTableObj getHostNodeTable();

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
	 *	Get the ICFSecHostNode instance which currently backs this instance.
	 *	<p>
	 *	This value <i>will</i> change for read-only instances, so you should
	 *	not hold on to the value as a reference anywhere outside the current call stack.
	 *
	 *	@return	ICFSecHostNode instance which currently backs this object.
	 */
	ICFSecHostNode getRec();

	/**
	 *	Internal use only.
	 */
	void setRec( ICFSecHostNode value );

	/**
	 *	Get the ICFSecHostNode instance which currently backs this instance.
	 *	<p>
	 *	This value <i>will</i> change for read-only instances, so you should
	 *	not hold on to the value as a reference anywhere outside the current call stack.
	 *
	 *	@return	ICFSecHostNode instance which currently backs this object.
	 */
	ICFSecHostNode getHostNodeRec();

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
	ICFSecClusterObj getRequiredContainerCluster();

	/**
	 *	Get the required ICFSecClusterObj instance referenced by the Cluster key.
	 *
	 *	@return	The required ICFSecClusterObj instance referenced by the Cluster key.
	 */
	ICFSecClusterObj getRequiredContainerCluster( boolean forceRead );

	/**
	 *	Get the array of optional ICFSecServiceObj array of instances referenced by the Service key.
	 *
	 *	@return	The optional ICFSecServiceObj[] array of instances referenced by the Service key.
	 */
	List<ICFSecServiceObj> getOptionalComponentsService();

	/**
	 *	Get the array of optional ICFSecServiceObj array of instances referenced by the Service key.
	 *
	 *	@return	The optional ICFSecServiceObj[] array of instances referenced by the Service key.
	 */
	List<ICFSecServiceObj> getOptionalComponentsService( boolean forceRead );

	/**
	 *	Get the required CFLibDbKeyHash256 attribute HostNodeId.
	 *
	 *	@return	The required CFLibDbKeyHash256 attribute HostNodeId.
	 */
	CFLibDbKeyHash256 getRequiredHostNodeId();

	/**
	 *	Get the required CFLibDbKeyHash256 attribute ClusterId.
	 *
	 *	@return	The required CFLibDbKeyHash256 attribute ClusterId.
	 */
	CFLibDbKeyHash256 getRequiredClusterId();

	/**
	 *	Get the required String attribute Description.
	 *
	 *	@return	The required String attribute Description.
	 */
	String getRequiredDescription();

	/**
	 *	Get the required String attribute HostName.
	 *
	 *	@return	The required String attribute HostName.
	 */
	String getRequiredHostName();

	/**
	 *	Internal use only.
	 */
	void copyPKeyToRec();

	/**
	 *	Internal use only.
	 */
	void copyRecToPKey();

}
