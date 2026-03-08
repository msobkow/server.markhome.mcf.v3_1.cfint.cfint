// Description: Java 25 Object interface for CFInt License.

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
import server.markhome.mcf.v3_1.cfint.cfint.*;

public interface ICFIntLicenseObj
	extends ICFLibAnyObj
{
	/**
	 *	Initially, the class code for an object is ICFIntLicense.CLASS_CODE, but the Obj layer relies on class code translation to map those
	 *	backing store entities to a runtime set of front-facing classcodes that the clients download and use when talking to the server implementing this code base.
	 *
	 *	@return The runtime class code used by this object. Only after the system is fully booted are these values stable and reliable.
	 */
	int getClassCode();
	/**
	 *	Realise this instance of a License.
	 *
	 *	@return	CFIntLicenseObj instance which should be subsequently referenced.
	 */
	ICFIntLicenseObj realise();

	/**
	 *	Forget this instance from the cache.
	 *	<p>
	 *	This method should only be invoked by implementation internals.
	 */
	void forget();

	/**
	 *	Re-read this instance by it's primary key.
	 *
	 *	@return	ICFIntLicenseObj the reference to the cached or read (realised) instance.
	 */
	ICFIntLicenseObj read();

	/**
	 *	Re-read this instance by it's primary key.
	 *
	 *	@return	ICFIntLicenseObj the reference to the cached or read (realised) instance.
	 */
	ICFIntLicenseObj read( boolean forceRead );

	/**
	 *	Initialize and return a locked edition of this License instance.
	 *
	 *	@return	The newly locked ICFIntLicenseEditObj edition of this instance.
	 */
	ICFIntLicenseEditObj beginEdit();

	/**
	 *	End this edition of this License instance.
	 *
	 *	@throws	CFLibNotSupportedException if you try to end a read-only view.
	 */
	void endEdit();

	/**
	 *	Get the current edition of this License instance.
	 *
	 *	@return	The ICFIntLicenseEditObj edition of this instance.
	 */
	ICFIntLicenseEditObj getEdit();

	/**
	 *	Get the current edition of this License instance as a ICFIntLicenseEditObj.
	 *
	 *	@return	The ICFIntLicenseEditObj edition of this instance.
	 */
	ICFIntLicenseEditObj getEditAsLicense();

	/**
	 *	Get the ICFIntLicenseTableObj table cache which manages this instance.
	 *
	 *	@return	ICFIntLicenseTableObj table cache which manages this instance.
	 */
	ICFIntLicenseTableObj getLicenseTable();

	/**
	 *	Get the ICFIntSchemaObj schema cache which manages this instance.
	 *
	 *	@return	ICFIntSchemaObj schema cache which manages this instance.
	 */
	ICFIntSchemaObj getSchema();

	/**
	 *	Set the ICFIntSchemaObj schema cache which manages this instance.
	 *	Should only be used to install overloads of the buff implementation wired specifically to a transport implementation
	 *	that eventually hits a server running a JPA backend.
	 *
	 *	@param schema	ICFIntSchemaObj schema cache which manages this instance.
	 */
	void setSchema(ICFIntSchemaObj schema);

	/**
	 *	Get the ICFIntLicense instance which currently backs this instance.
	 *	<p>
	 *	This value <i>will</i> change for read-only instances, so you should
	 *	not hold on to the value as a reference anywhere outside the current call stack.
	 *
	 *	@return	ICFIntLicense instance which currently backs this object.
	 */
	ICFIntLicense getRec();

	/**
	 *	Internal use only.
	 */
	void setRec( ICFIntLicense value );

	/**
	 *	Get the ICFIntLicense instance which currently backs this instance.
	 *	<p>
	 *	This value <i>will</i> change for read-only instances, so you should
	 *	not hold on to the value as a reference anywhere outside the current call stack.
	 *
	 *	@return	ICFIntLicense instance which currently backs this object.
	 */
	ICFIntLicense getLicenseRec();

	/**
	 *	Get the primary key of this instance.
	 *
	 *	@return	CFLibDbKeyHash256 primary key for this instance.
	 */
	CFLibDbKeyHash256 getPKey();

	/**
	 *	Set the primary key of this instance.
	 *	<p>
	 *	This method should only be invoked by implementation internals.
	 *
	 *	@param CFLibDbKeyHash256 primary key value for this instance.
	 */
	void setPKey( CFLibDbKeyHash256 value );

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
	 *	Get the required ICFIntTenantObj instance referenced by the Tenant key.
	 *
	 *	@return	The required ICFIntTenantObj instance referenced by the Tenant key.
	 */
	ICFSecTenantObj getRequiredOwnerTenant();

	/**
	 *	Get the required ICFSecTenantObj instance referenced by the Tenant key.
	 *
	 *	@return	The required ICFSecTenantObj instance referenced by the Tenant key.
	 */
	ICFSecTenantObj getRequiredOwnerTenant( boolean forceRead );

	/**
	 *	Get the required ICFIntTopDomainObj instance referenced by the TopDomain key.
	 *
	 *	@return	The required ICFIntTopDomainObj instance referenced by the TopDomain key.
	 */
	ICFIntTopDomainObj getRequiredContainerTopDomain();

	/**
	 *	Get the required ICFIntTopDomainObj instance referenced by the TopDomain key.
	 *
	 *	@return	The required ICFIntTopDomainObj instance referenced by the TopDomain key.
	 */
	ICFIntTopDomainObj getRequiredContainerTopDomain( boolean forceRead );

	/**
	 *	Get the required CFLibDbKeyHash256 attribute Id.
	 *
	 *	@return	The required CFLibDbKeyHash256 attribute Id.
	 */
	CFLibDbKeyHash256 getRequiredId();

	/**
	 *	Get the required CFLibDbKeyHash256 attribute TenantId.
	 *
	 *	@return	The required CFLibDbKeyHash256 attribute TenantId.
	 */
	CFLibDbKeyHash256 getRequiredTenantId();

	/**
	 *	Get the required CFLibDbKeyHash256 attribute TopDomainId.
	 *
	 *	@return	The required CFLibDbKeyHash256 attribute TopDomainId.
	 */
	CFLibDbKeyHash256 getRequiredTopDomainId();

	/**
	 *	Get the required String attribute Name.
	 *
	 *	@return	The required String attribute Name.
	 */
	String getRequiredName();

	/**
	 *	Get the optional String attribute Description.
	 *
	 *	@return	The optional String attribute Description.
	 */
	String getOptionalDescription();

	/**
	 *	Get the optional String attribute EmbeddedText.
	 *
	 *	@return	The optional String attribute EmbeddedText.
	 */
	String getOptionalEmbeddedText();

	/**
	 *	Get the optional String attribute FullText.
	 *
	 *	@return	The optional String attribute FullText.
	 */
	String getOptionalFullText();

	/**
	 *	Internal use only.
	 */
	void copyPKeyToRec();

	/**
	 *	Internal use only.
	 */
	void copyRecToPKey();

}
