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

public interface ICFIntMajorVersionTableObj
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
	 *	Instantiate a new MajorVersion instance.
	 *
	 *	@return	A new instance.
	 */
	ICFIntMajorVersionObj newInstance();

	/**
	 *	Instantiate a new MajorVersion edition of the specified MajorVersion instance.
	 *
	 *	@return	A new edition.
	 */
	ICFIntMajorVersionEditObj newEditInstance( ICFIntMajorVersionObj orig );

	/**
	 *	Internal use only.
	 */
	ICFIntMajorVersionObj realiseMajorVersion( ICFIntMajorVersionObj Obj );

	/**
	 *	Internal use only.
	 */
	ICFIntMajorVersionObj createMajorVersion( ICFIntMajorVersionObj Obj );

	/**
	 *	Read a MajorVersion-derived instance by it's primary key.
	 *
	 *	@param	pkey	The primary key identifying the instance to read.
	 *
	 *	@return	The MajorVersion-derived instance identified by the primary key,
	 *		or null if no such key value exists.
	 */
	ICFIntMajorVersionObj readMajorVersion( CFLibDbKeyHash256 pkey );

	/**
	 *	Read a MajorVersion-derived instance by it's primary key.
	 *
	 *	@param	pkey	The primary key identifying the instance to read.
	 *
	 *	@return	The MajorVersion-derived instance identified by the primary key,
	 *		or null if no such key value exists.
	 */
	ICFIntMajorVersionObj readMajorVersion( CFLibDbKeyHash256 pkey,
		boolean forceRead );

	ICFIntMajorVersionObj readCachedMajorVersion( CFLibDbKeyHash256 pkey );

	public void reallyDeepDisposeMajorVersion( ICFIntMajorVersionObj obj );

	void deepDisposeMajorVersion( CFLibDbKeyHash256 pkey );

	/**
	 *	Internal use only.
	 */
	ICFIntMajorVersionObj lockMajorVersion( CFLibDbKeyHash256 pkey );

	/**
	 *	Return a sorted list of all the MajorVersion-derived instances in the database.
	 *
	 *	@return	List of ICFIntMajorVersionObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	List<ICFIntMajorVersionObj> readAllMajorVersion();

	/**
	 *	Return a sorted map of all the MajorVersion-derived instances in the database.
	 *
	 *	@return	List of ICFIntMajorVersionObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	List<ICFIntMajorVersionObj> readAllMajorVersion( boolean forceRead );

	List<ICFIntMajorVersionObj> readCachedAllMajorVersion();

	/**
	 *	Get the CFIntMajorVersionObj instance for the primary key attributes.
	 *
	 *	@param	Id	The MajorVersion key attribute of the instance generating the id.
	 *
	 *	@return	CFIntMajorVersionObj cached instance for the primary key, or
	 *		null if no such instance exists.
	 */
	ICFIntMajorVersionObj readMajorVersionByIdIdx( CFLibDbKeyHash256 Id );

	/**
	 *	Get the CFIntMajorVersionObj instance for the primary key attributes.
	 *
	 *	@param	Id	The MajorVersion key attribute of the instance generating the id.
	 *
	 *	@return	CFIntMajorVersionObj refreshed instance for the primary key, or
	 *		null if no such instance exists.
	 */
	ICFIntMajorVersionObj readMajorVersionByIdIdx( CFLibDbKeyHash256 Id,
		boolean forceRead );

	/**
	 *	Get the map of CFIntMajorVersionObj instances sorted by their primary keys for the duplicate TenantIdx key.
	 *
	 *	@param	TenantId	The MajorVersion key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntMajorVersionObj cached instances sorted by their primary keys for the duplicate TenantIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntMajorVersionObj> readMajorVersionByTenantIdx( CFLibDbKeyHash256 TenantId );

	/**
	 *	Get the map of CFIntMajorVersionObj instances sorted by their primary keys for the duplicate TenantIdx key.
	 *
	 *	@param	TenantId	The MajorVersion key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntMajorVersionObj cached instances sorted by their primary keys for the duplicate TenantIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntMajorVersionObj> readMajorVersionByTenantIdx( CFLibDbKeyHash256 TenantId,
		boolean forceRead );

	/**
	 *	Get the map of CFIntMajorVersionObj instances sorted by their primary keys for the duplicate SubProjectIdx key.
	 *
	 *	@param	SubProjectId	The MajorVersion key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntMajorVersionObj cached instances sorted by their primary keys for the duplicate SubProjectIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntMajorVersionObj> readMajorVersionBySubProjectIdx( CFLibDbKeyHash256 SubProjectId );

	/**
	 *	Get the map of CFIntMajorVersionObj instances sorted by their primary keys for the duplicate SubProjectIdx key.
	 *
	 *	@param	SubProjectId	The MajorVersion key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntMajorVersionObj cached instances sorted by their primary keys for the duplicate SubProjectIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntMajorVersionObj> readMajorVersionBySubProjectIdx( CFLibDbKeyHash256 SubProjectId,
		boolean forceRead );

	/**
	 *	Get the CFIntMajorVersionObj instance for the unique NameIdx key.
	 *
	 *	@param	SubProjectId	The MajorVersion key attribute of the instance generating the id.
	 *
	 *	@param	Name	The MajorVersion key attribute of the instance generating the id.
	 *
	 *	@return	CFIntMajorVersionObj cached instance for the unique NameIdx key, or
	 *		null if no such instance exists.
	 */
	ICFIntMajorVersionObj readMajorVersionByNameIdx(CFLibDbKeyHash256 SubProjectId,
		String Name );

	/**
	 *	Get the CFIntMajorVersionObj instance for the unique NameIdx key.
	 *
	 *	@param	SubProjectId	The MajorVersion key attribute of the instance generating the id.
	 *
	 *	@param	Name	The MajorVersion key attribute of the instance generating the id.
	 *
	 *	@return	CFIntMajorVersionObj refreshed instance for the unique NameIdx key, or
	 *		null if no such instance exists.
	 */
	ICFIntMajorVersionObj readMajorVersionByNameIdx(CFLibDbKeyHash256 SubProjectId,
		String Name,
		boolean forceRead );

	ICFIntMajorVersionObj readCachedMajorVersionByIdIdx( CFLibDbKeyHash256 Id );

	List<ICFIntMajorVersionObj> readCachedMajorVersionByTenantIdx( CFLibDbKeyHash256 TenantId );

	List<ICFIntMajorVersionObj> readCachedMajorVersionBySubProjectIdx( CFLibDbKeyHash256 SubProjectId );

	ICFIntMajorVersionObj readCachedMajorVersionByNameIdx( CFLibDbKeyHash256 SubProjectId,
		String Name );

	void deepDisposeMajorVersionByIdIdx( CFLibDbKeyHash256 Id );

	void deepDisposeMajorVersionByTenantIdx( CFLibDbKeyHash256 TenantId );

	void deepDisposeMajorVersionBySubProjectIdx( CFLibDbKeyHash256 SubProjectId );

	void deepDisposeMajorVersionByNameIdx( CFLibDbKeyHash256 SubProjectId,
		String Name );

	/**
	 *	Internal use only.
	 */
	ICFIntMajorVersionObj updateMajorVersion( ICFIntMajorVersionObj Obj );

	/**
	 *	Internal use only.
	 */
	void deleteMajorVersion( ICFIntMajorVersionObj Obj );

	/**
	 *	Internal use only.
	 *
	 *	@param	Id	The MajorVersion key attribute of the instance generating the id.
	 */
	void deleteMajorVersionByIdIdx( CFLibDbKeyHash256 Id );

	/**
	 *	Internal use only.
	 *
	 *	@param	TenantId	The MajorVersion key attribute of the instance generating the id.
	 */
	void deleteMajorVersionByTenantIdx( CFLibDbKeyHash256 TenantId );

	/**
	 *	Internal use only.
	 *
	 *	@param	SubProjectId	The MajorVersion key attribute of the instance generating the id.
	 */
	void deleteMajorVersionBySubProjectIdx( CFLibDbKeyHash256 SubProjectId );

	/**
	 *	Internal use only.
	 *
	 *	@param	SubProjectId	The MajorVersion key attribute of the instance generating the id.
	 *
	 *	@param	Name	The MajorVersion key attribute of the instance generating the id.
	 */
	void deleteMajorVersionByNameIdx(CFLibDbKeyHash256 SubProjectId,
		String Name );
}
