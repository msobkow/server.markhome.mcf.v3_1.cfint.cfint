// Description: Java 25 Table Object interface for CFInt.

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

public interface ICFIntLicenseTableObj
{
	public ICFIntSchemaObj getSchema();
	public void setSchema( ICFIntSchemaObj value );

	public void minimizeMemory();

	public String getTableName();
	public String getTableDbName();

	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	public int getClassCode();

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	// public static int getBackingClassCode();

	Class getObjQualifyingClass();

	/**
	 *	Instantiate a new License instance.
	 *
	 *	@return	A new instance.
	 */
	ICFIntLicenseObj newInstance();

	/**
	 *	Instantiate a new License edition of the specified License instance.
	 *
	 *	@return	A new edition.
	 */
	ICFIntLicenseEditObj newEditInstance( ICFIntLicenseObj orig );

	/**
	 *	Internal use only.
	 */
	ICFIntLicenseObj realiseLicense( ICFIntLicenseObj Obj );

	/**
	 *	Internal use only.
	 */
	ICFIntLicenseObj createLicense( ICFIntLicenseObj Obj );

	/**
	 *	Read a License-derived instance by it's primary key.
	 *
	 *	@param	pkey	The primary key identifying the instance to read.
	 *
	 *	@return	The License-derived instance identified by the primary key,
	 *		or null if no such key value exists.
	 */
	ICFIntLicenseObj readLicense( CFLibDbKeyHash256 pkey );

	/**
	 *	Read a License-derived instance by it's primary key.
	 *
	 *	@param	pkey	The primary key identifying the instance to read.
	 *
	 *	@return	The License-derived instance identified by the primary key,
	 *		or null if no such key value exists.
	 */
	ICFIntLicenseObj readLicense( CFLibDbKeyHash256 pkey,
		boolean forceRead );

	ICFIntLicenseObj readCachedLicense( CFLibDbKeyHash256 pkey );

	public void reallyDeepDisposeLicense( ICFIntLicenseObj obj );

	void deepDisposeLicense( CFLibDbKeyHash256 pkey );

	/**
	 *	Internal use only.
	 */
	ICFIntLicenseObj lockLicense( CFLibDbKeyHash256 pkey );

	/**
	 *	Return a sorted list of all the License-derived instances in the database.
	 *
	 *	@return	List of ICFIntLicenseObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	List<ICFIntLicenseObj> readAllLicense();

	/**
	 *	Return a sorted map of all the License-derived instances in the database.
	 *
	 *	@return	List of ICFIntLicenseObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	List<ICFIntLicenseObj> readAllLicense( boolean forceRead );

	List<ICFIntLicenseObj> readCachedAllLicense();

	/**
	 *	Get the CFIntLicenseObj instance for the primary key attributes.
	 *
	 *	@param	Id	The License key attribute of the instance generating the id.
	 *
	 *	@return	CFIntLicenseObj cached instance for the primary key, or
	 *		null if no such instance exists.
	 */
	ICFIntLicenseObj readLicenseByIdIdx( CFLibDbKeyHash256 Id );

	/**
	 *	Get the CFIntLicenseObj instance for the primary key attributes.
	 *
	 *	@param	Id	The License key attribute of the instance generating the id.
	 *
	 *	@return	CFIntLicenseObj refreshed instance for the primary key, or
	 *		null if no such instance exists.
	 */
	ICFIntLicenseObj readLicenseByIdIdx( CFLibDbKeyHash256 Id,
		boolean forceRead );

	/**
	 *	Get the map of CFIntLicenseObj instances sorted by their primary keys for the duplicate LicnTenantIdx key.
	 *
	 *	@param	TenantId	The License key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntLicenseObj cached instances sorted by their primary keys for the duplicate LicnTenantIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntLicenseObj> readLicenseByLicnTenantIdx( CFLibDbKeyHash256 TenantId );

	/**
	 *	Get the map of CFIntLicenseObj instances sorted by their primary keys for the duplicate LicnTenantIdx key.
	 *
	 *	@param	TenantId	The License key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntLicenseObj cached instances sorted by their primary keys for the duplicate LicnTenantIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntLicenseObj> readLicenseByLicnTenantIdx( CFLibDbKeyHash256 TenantId,
		boolean forceRead );

	/**
	 *	Get the map of CFIntLicenseObj instances sorted by their primary keys for the duplicate DomainIdx key.
	 *
	 *	@param	TopDomainId	The License key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntLicenseObj cached instances sorted by their primary keys for the duplicate DomainIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntLicenseObj> readLicenseByDomainIdx( CFLibDbKeyHash256 TopDomainId );

	/**
	 *	Get the map of CFIntLicenseObj instances sorted by their primary keys for the duplicate DomainIdx key.
	 *
	 *	@param	TopDomainId	The License key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntLicenseObj cached instances sorted by their primary keys for the duplicate DomainIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntLicenseObj> readLicenseByDomainIdx( CFLibDbKeyHash256 TopDomainId,
		boolean forceRead );

	/**
	 *	Get the CFIntLicenseObj instance for the unique UNameIdx key.
	 *
	 *	@param	TopDomainId	The License key attribute of the instance generating the id.
	 *
	 *	@param	Name	The License key attribute of the instance generating the id.
	 *
	 *	@return	CFIntLicenseObj cached instance for the unique UNameIdx key, or
	 *		null if no such instance exists.
	 */
	ICFIntLicenseObj readLicenseByUNameIdx(CFLibDbKeyHash256 TopDomainId,
		String Name );

	/**
	 *	Get the CFIntLicenseObj instance for the unique UNameIdx key.
	 *
	 *	@param	TopDomainId	The License key attribute of the instance generating the id.
	 *
	 *	@param	Name	The License key attribute of the instance generating the id.
	 *
	 *	@return	CFIntLicenseObj refreshed instance for the unique UNameIdx key, or
	 *		null if no such instance exists.
	 */
	ICFIntLicenseObj readLicenseByUNameIdx(CFLibDbKeyHash256 TopDomainId,
		String Name,
		boolean forceRead );

	ICFIntLicenseObj readCachedLicenseByIdIdx( CFLibDbKeyHash256 Id );

	List<ICFIntLicenseObj> readCachedLicenseByLicnTenantIdx( CFLibDbKeyHash256 TenantId );

	List<ICFIntLicenseObj> readCachedLicenseByDomainIdx( CFLibDbKeyHash256 TopDomainId );

	ICFIntLicenseObj readCachedLicenseByUNameIdx( CFLibDbKeyHash256 TopDomainId,
		String Name );

	void deepDisposeLicenseByIdIdx( CFLibDbKeyHash256 Id );

	void deepDisposeLicenseByLicnTenantIdx( CFLibDbKeyHash256 TenantId );

	void deepDisposeLicenseByDomainIdx( CFLibDbKeyHash256 TopDomainId );

	void deepDisposeLicenseByUNameIdx( CFLibDbKeyHash256 TopDomainId,
		String Name );

	/**
	 *	Internal use only.
	 */
	ICFIntLicenseObj updateLicense( ICFIntLicenseObj Obj );

	/**
	 *	Internal use only.
	 */
	void deleteLicense( ICFIntLicenseObj Obj );

	/**
	 *	Internal use only.
	 *
	 *	@param	Id	The License key attribute of the instance generating the id.
	 */
	void deleteLicenseByIdIdx( CFLibDbKeyHash256 Id );

	/**
	 *	Internal use only.
	 *
	 *	@param	TenantId	The License key attribute of the instance generating the id.
	 */
	void deleteLicenseByLicnTenantIdx( CFLibDbKeyHash256 TenantId );

	/**
	 *	Internal use only.
	 *
	 *	@param	TopDomainId	The License key attribute of the instance generating the id.
	 */
	void deleteLicenseByDomainIdx( CFLibDbKeyHash256 TopDomainId );

	/**
	 *	Internal use only.
	 *
	 *	@param	TopDomainId	The License key attribute of the instance generating the id.
	 *
	 *	@param	Name	The License key attribute of the instance generating the id.
	 */
	void deleteLicenseByUNameIdx(CFLibDbKeyHash256 TopDomainId,
		String Name );
}
