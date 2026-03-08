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

public interface ICFIntSubProjectTableObj
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
	 *	Instantiate a new SubProject instance.
	 *
	 *	@return	A new instance.
	 */
	ICFIntSubProjectObj newInstance();

	/**
	 *	Instantiate a new SubProject edition of the specified SubProject instance.
	 *
	 *	@return	A new edition.
	 */
	ICFIntSubProjectEditObj newEditInstance( ICFIntSubProjectObj orig );

	/**
	 *	Internal use only.
	 */
	ICFIntSubProjectObj realiseSubProject( ICFIntSubProjectObj Obj );

	/**
	 *	Internal use only.
	 */
	ICFIntSubProjectObj createSubProject( ICFIntSubProjectObj Obj );

	/**
	 *	Read a SubProject-derived instance by it's primary key.
	 *
	 *	@param	pkey	The primary key identifying the instance to read.
	 *
	 *	@return	The SubProject-derived instance identified by the primary key,
	 *		or null if no such key value exists.
	 */
	ICFIntSubProjectObj readSubProject( CFLibDbKeyHash256 pkey );

	/**
	 *	Read a SubProject-derived instance by it's primary key.
	 *
	 *	@param	pkey	The primary key identifying the instance to read.
	 *
	 *	@return	The SubProject-derived instance identified by the primary key,
	 *		or null if no such key value exists.
	 */
	ICFIntSubProjectObj readSubProject( CFLibDbKeyHash256 pkey,
		boolean forceRead );

	ICFIntSubProjectObj readCachedSubProject( CFLibDbKeyHash256 pkey );

	public void reallyDeepDisposeSubProject( ICFIntSubProjectObj obj );

	void deepDisposeSubProject( CFLibDbKeyHash256 pkey );

	/**
	 *	Internal use only.
	 */
	ICFIntSubProjectObj lockSubProject( CFLibDbKeyHash256 pkey );

	/**
	 *	Return a sorted list of all the SubProject-derived instances in the database.
	 *
	 *	@return	List of ICFIntSubProjectObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	List<ICFIntSubProjectObj> readAllSubProject();

	/**
	 *	Return a sorted map of all the SubProject-derived instances in the database.
	 *
	 *	@return	List of ICFIntSubProjectObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	List<ICFIntSubProjectObj> readAllSubProject( boolean forceRead );

	List<ICFIntSubProjectObj> readCachedAllSubProject();

	/**
	 *	Get the CFIntSubProjectObj instance for the primary key attributes.
	 *
	 *	@param	Id	The SubProject key attribute of the instance generating the id.
	 *
	 *	@return	CFIntSubProjectObj cached instance for the primary key, or
	 *		null if no such instance exists.
	 */
	ICFIntSubProjectObj readSubProjectByIdIdx( CFLibDbKeyHash256 Id );

	/**
	 *	Get the CFIntSubProjectObj instance for the primary key attributes.
	 *
	 *	@param	Id	The SubProject key attribute of the instance generating the id.
	 *
	 *	@return	CFIntSubProjectObj refreshed instance for the primary key, or
	 *		null if no such instance exists.
	 */
	ICFIntSubProjectObj readSubProjectByIdIdx( CFLibDbKeyHash256 Id,
		boolean forceRead );

	/**
	 *	Get the map of CFIntSubProjectObj instances sorted by their primary keys for the duplicate TenantIdx key.
	 *
	 *	@param	TenantId	The SubProject key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntSubProjectObj cached instances sorted by their primary keys for the duplicate TenantIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntSubProjectObj> readSubProjectByTenantIdx( CFLibDbKeyHash256 TenantId );

	/**
	 *	Get the map of CFIntSubProjectObj instances sorted by their primary keys for the duplicate TenantIdx key.
	 *
	 *	@param	TenantId	The SubProject key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntSubProjectObj cached instances sorted by their primary keys for the duplicate TenantIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntSubProjectObj> readSubProjectByTenantIdx( CFLibDbKeyHash256 TenantId,
		boolean forceRead );

	/**
	 *	Get the map of CFIntSubProjectObj instances sorted by their primary keys for the duplicate TopProjectIdx key.
	 *
	 *	@param	TopProjectId	The SubProject key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntSubProjectObj cached instances sorted by their primary keys for the duplicate TopProjectIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntSubProjectObj> readSubProjectByTopProjectIdx( CFLibDbKeyHash256 TopProjectId );

	/**
	 *	Get the map of CFIntSubProjectObj instances sorted by their primary keys for the duplicate TopProjectIdx key.
	 *
	 *	@param	TopProjectId	The SubProject key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntSubProjectObj cached instances sorted by their primary keys for the duplicate TopProjectIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntSubProjectObj> readSubProjectByTopProjectIdx( CFLibDbKeyHash256 TopProjectId,
		boolean forceRead );

	/**
	 *	Get the CFIntSubProjectObj instance for the unique NameIdx key.
	 *
	 *	@param	TopProjectId	The SubProject key attribute of the instance generating the id.
	 *
	 *	@param	Name	The SubProject key attribute of the instance generating the id.
	 *
	 *	@return	CFIntSubProjectObj cached instance for the unique NameIdx key, or
	 *		null if no such instance exists.
	 */
	ICFIntSubProjectObj readSubProjectByNameIdx(CFLibDbKeyHash256 TopProjectId,
		String Name );

	/**
	 *	Get the CFIntSubProjectObj instance for the unique NameIdx key.
	 *
	 *	@param	TopProjectId	The SubProject key attribute of the instance generating the id.
	 *
	 *	@param	Name	The SubProject key attribute of the instance generating the id.
	 *
	 *	@return	CFIntSubProjectObj refreshed instance for the unique NameIdx key, or
	 *		null if no such instance exists.
	 */
	ICFIntSubProjectObj readSubProjectByNameIdx(CFLibDbKeyHash256 TopProjectId,
		String Name,
		boolean forceRead );

	ICFIntSubProjectObj readCachedSubProjectByIdIdx( CFLibDbKeyHash256 Id );

	List<ICFIntSubProjectObj> readCachedSubProjectByTenantIdx( CFLibDbKeyHash256 TenantId );

	List<ICFIntSubProjectObj> readCachedSubProjectByTopProjectIdx( CFLibDbKeyHash256 TopProjectId );

	ICFIntSubProjectObj readCachedSubProjectByNameIdx( CFLibDbKeyHash256 TopProjectId,
		String Name );

	void deepDisposeSubProjectByIdIdx( CFLibDbKeyHash256 Id );

	void deepDisposeSubProjectByTenantIdx( CFLibDbKeyHash256 TenantId );

	void deepDisposeSubProjectByTopProjectIdx( CFLibDbKeyHash256 TopProjectId );

	void deepDisposeSubProjectByNameIdx( CFLibDbKeyHash256 TopProjectId,
		String Name );

	/**
	 *	Internal use only.
	 */
	ICFIntSubProjectObj updateSubProject( ICFIntSubProjectObj Obj );

	/**
	 *	Internal use only.
	 */
	void deleteSubProject( ICFIntSubProjectObj Obj );

	/**
	 *	Internal use only.
	 *
	 *	@param	Id	The SubProject key attribute of the instance generating the id.
	 */
	void deleteSubProjectByIdIdx( CFLibDbKeyHash256 Id );

	/**
	 *	Internal use only.
	 *
	 *	@param	TenantId	The SubProject key attribute of the instance generating the id.
	 */
	void deleteSubProjectByTenantIdx( CFLibDbKeyHash256 TenantId );

	/**
	 *	Internal use only.
	 *
	 *	@param	TopProjectId	The SubProject key attribute of the instance generating the id.
	 */
	void deleteSubProjectByTopProjectIdx( CFLibDbKeyHash256 TopProjectId );

	/**
	 *	Internal use only.
	 *
	 *	@param	TopProjectId	The SubProject key attribute of the instance generating the id.
	 *
	 *	@param	Name	The SubProject key attribute of the instance generating the id.
	 */
	void deleteSubProjectByNameIdx(CFLibDbKeyHash256 TopProjectId,
		String Name );
}
