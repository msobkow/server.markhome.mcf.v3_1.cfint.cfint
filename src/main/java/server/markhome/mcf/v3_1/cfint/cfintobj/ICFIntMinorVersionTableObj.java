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

public interface ICFIntMinorVersionTableObj
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
	 *	Instantiate a new MinorVersion instance.
	 *
	 *	@return	A new instance.
	 */
	ICFIntMinorVersionObj newInstance();

	/**
	 *	Instantiate a new MinorVersion edition of the specified MinorVersion instance.
	 *
	 *	@return	A new edition.
	 */
	ICFIntMinorVersionEditObj newEditInstance( ICFIntMinorVersionObj orig );

	/**
	 *	Internal use only.
	 */
	ICFIntMinorVersionObj realiseMinorVersion( ICFIntMinorVersionObj Obj );

	/**
	 *	Internal use only.
	 */
	ICFIntMinorVersionObj createMinorVersion( ICFIntMinorVersionObj Obj );

	/**
	 *	Read a MinorVersion-derived instance by it's primary key.
	 *
	 *	@param	pkey	The primary key identifying the instance to read.
	 *
	 *	@return	The MinorVersion-derived instance identified by the primary key,
	 *		or null if no such key value exists.
	 */
	ICFIntMinorVersionObj readMinorVersion( CFLibDbKeyHash256 pkey );

	/**
	 *	Read a MinorVersion-derived instance by it's primary key.
	 *
	 *	@param	pkey	The primary key identifying the instance to read.
	 *
	 *	@return	The MinorVersion-derived instance identified by the primary key,
	 *		or null if no such key value exists.
	 */
	ICFIntMinorVersionObj readMinorVersion( CFLibDbKeyHash256 pkey,
		boolean forceRead );

	ICFIntMinorVersionObj readCachedMinorVersion( CFLibDbKeyHash256 pkey );

	public void reallyDeepDisposeMinorVersion( ICFIntMinorVersionObj obj );

	void deepDisposeMinorVersion( CFLibDbKeyHash256 pkey );

	/**
	 *	Internal use only.
	 */
	ICFIntMinorVersionObj lockMinorVersion( CFLibDbKeyHash256 pkey );

	/**
	 *	Return a sorted list of all the MinorVersion-derived instances in the database.
	 *
	 *	@return	List of ICFIntMinorVersionObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	List<ICFIntMinorVersionObj> readAllMinorVersion();

	/**
	 *	Return a sorted map of all the MinorVersion-derived instances in the database.
	 *
	 *	@return	List of ICFIntMinorVersionObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	List<ICFIntMinorVersionObj> readAllMinorVersion( boolean forceRead );

	List<ICFIntMinorVersionObj> readCachedAllMinorVersion();

	/**
	 *	Get the CFIntMinorVersionObj instance for the primary key attributes.
	 *
	 *	@param	Id	The MinorVersion key attribute of the instance generating the id.
	 *
	 *	@return	CFIntMinorVersionObj cached instance for the primary key, or
	 *		null if no such instance exists.
	 */
	ICFIntMinorVersionObj readMinorVersionByIdIdx( CFLibDbKeyHash256 Id );

	/**
	 *	Get the CFIntMinorVersionObj instance for the primary key attributes.
	 *
	 *	@param	Id	The MinorVersion key attribute of the instance generating the id.
	 *
	 *	@return	CFIntMinorVersionObj refreshed instance for the primary key, or
	 *		null if no such instance exists.
	 */
	ICFIntMinorVersionObj readMinorVersionByIdIdx( CFLibDbKeyHash256 Id,
		boolean forceRead );

	/**
	 *	Get the map of CFIntMinorVersionObj instances sorted by their primary keys for the duplicate TenantIdx key.
	 *
	 *	@param	TenantId	The MinorVersion key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntMinorVersionObj cached instances sorted by their primary keys for the duplicate TenantIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntMinorVersionObj> readMinorVersionByTenantIdx( CFLibDbKeyHash256 TenantId );

	/**
	 *	Get the map of CFIntMinorVersionObj instances sorted by their primary keys for the duplicate TenantIdx key.
	 *
	 *	@param	TenantId	The MinorVersion key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntMinorVersionObj cached instances sorted by their primary keys for the duplicate TenantIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntMinorVersionObj> readMinorVersionByTenantIdx( CFLibDbKeyHash256 TenantId,
		boolean forceRead );

	/**
	 *	Get the map of CFIntMinorVersionObj instances sorted by their primary keys for the duplicate MajorVerIdx key.
	 *
	 *	@param	MajorVersionId	The MinorVersion key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntMinorVersionObj cached instances sorted by their primary keys for the duplicate MajorVerIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntMinorVersionObj> readMinorVersionByMajorVerIdx( CFLibDbKeyHash256 MajorVersionId );

	/**
	 *	Get the map of CFIntMinorVersionObj instances sorted by their primary keys for the duplicate MajorVerIdx key.
	 *
	 *	@param	MajorVersionId	The MinorVersion key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntMinorVersionObj cached instances sorted by their primary keys for the duplicate MajorVerIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntMinorVersionObj> readMinorVersionByMajorVerIdx( CFLibDbKeyHash256 MajorVersionId,
		boolean forceRead );

	/**
	 *	Get the CFIntMinorVersionObj instance for the unique NameIdx key.
	 *
	 *	@param	MajorVersionId	The MinorVersion key attribute of the instance generating the id.
	 *
	 *	@param	Name	The MinorVersion key attribute of the instance generating the id.
	 *
	 *	@return	CFIntMinorVersionObj cached instance for the unique NameIdx key, or
	 *		null if no such instance exists.
	 */
	ICFIntMinorVersionObj readMinorVersionByNameIdx(CFLibDbKeyHash256 MajorVersionId,
		String Name );

	/**
	 *	Get the CFIntMinorVersionObj instance for the unique NameIdx key.
	 *
	 *	@param	MajorVersionId	The MinorVersion key attribute of the instance generating the id.
	 *
	 *	@param	Name	The MinorVersion key attribute of the instance generating the id.
	 *
	 *	@return	CFIntMinorVersionObj refreshed instance for the unique NameIdx key, or
	 *		null if no such instance exists.
	 */
	ICFIntMinorVersionObj readMinorVersionByNameIdx(CFLibDbKeyHash256 MajorVersionId,
		String Name,
		boolean forceRead );

	ICFIntMinorVersionObj readCachedMinorVersionByIdIdx( CFLibDbKeyHash256 Id );

	List<ICFIntMinorVersionObj> readCachedMinorVersionByTenantIdx( CFLibDbKeyHash256 TenantId );

	List<ICFIntMinorVersionObj> readCachedMinorVersionByMajorVerIdx( CFLibDbKeyHash256 MajorVersionId );

	ICFIntMinorVersionObj readCachedMinorVersionByNameIdx( CFLibDbKeyHash256 MajorVersionId,
		String Name );

	void deepDisposeMinorVersionByIdIdx( CFLibDbKeyHash256 Id );

	void deepDisposeMinorVersionByTenantIdx( CFLibDbKeyHash256 TenantId );

	void deepDisposeMinorVersionByMajorVerIdx( CFLibDbKeyHash256 MajorVersionId );

	void deepDisposeMinorVersionByNameIdx( CFLibDbKeyHash256 MajorVersionId,
		String Name );

	/**
	 *	Internal use only.
	 */
	ICFIntMinorVersionObj updateMinorVersion( ICFIntMinorVersionObj Obj );

	/**
	 *	Internal use only.
	 */
	void deleteMinorVersion( ICFIntMinorVersionObj Obj );

	/**
	 *	Internal use only.
	 *
	 *	@param	Id	The MinorVersion key attribute of the instance generating the id.
	 */
	void deleteMinorVersionByIdIdx( CFLibDbKeyHash256 Id );

	/**
	 *	Internal use only.
	 *
	 *	@param	TenantId	The MinorVersion key attribute of the instance generating the id.
	 */
	void deleteMinorVersionByTenantIdx( CFLibDbKeyHash256 TenantId );

	/**
	 *	Internal use only.
	 *
	 *	@param	MajorVersionId	The MinorVersion key attribute of the instance generating the id.
	 */
	void deleteMinorVersionByMajorVerIdx( CFLibDbKeyHash256 MajorVersionId );

	/**
	 *	Internal use only.
	 *
	 *	@param	MajorVersionId	The MinorVersion key attribute of the instance generating the id.
	 *
	 *	@param	Name	The MinorVersion key attribute of the instance generating the id.
	 */
	void deleteMinorVersionByNameIdx(CFLibDbKeyHash256 MajorVersionId,
		String Name );
}
