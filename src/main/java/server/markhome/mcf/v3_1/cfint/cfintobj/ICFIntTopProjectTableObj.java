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

public interface ICFIntTopProjectTableObj
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
	 *	Instantiate a new TopProject instance.
	 *
	 *	@return	A new instance.
	 */
	ICFIntTopProjectObj newInstance();

	/**
	 *	Instantiate a new TopProject edition of the specified TopProject instance.
	 *
	 *	@return	A new edition.
	 */
	ICFIntTopProjectEditObj newEditInstance( ICFIntTopProjectObj orig );

	/**
	 *	Internal use only.
	 */
	ICFIntTopProjectObj realiseTopProject( ICFIntTopProjectObj Obj );

	/**
	 *	Internal use only.
	 */
	ICFIntTopProjectObj createTopProject( ICFIntTopProjectObj Obj );

	/**
	 *	Read a TopProject-derived instance by it's primary key.
	 *
	 *	@param	pkey	The primary key identifying the instance to read.
	 *
	 *	@return	The TopProject-derived instance identified by the primary key,
	 *		or null if no such key value exists.
	 */
	ICFIntTopProjectObj readTopProject( CFLibDbKeyHash256 pkey );

	/**
	 *	Read a TopProject-derived instance by it's primary key.
	 *
	 *	@param	pkey	The primary key identifying the instance to read.
	 *
	 *	@return	The TopProject-derived instance identified by the primary key,
	 *		or null if no such key value exists.
	 */
	ICFIntTopProjectObj readTopProject( CFLibDbKeyHash256 pkey,
		boolean forceRead );

	ICFIntTopProjectObj readCachedTopProject( CFLibDbKeyHash256 pkey );

	public void reallyDeepDisposeTopProject( ICFIntTopProjectObj obj );

	void deepDisposeTopProject( CFLibDbKeyHash256 pkey );

	/**
	 *	Internal use only.
	 */
	ICFIntTopProjectObj lockTopProject( CFLibDbKeyHash256 pkey );

	/**
	 *	Return a sorted list of all the TopProject-derived instances in the database.
	 *
	 *	@return	List of ICFIntTopProjectObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	List<ICFIntTopProjectObj> readAllTopProject();

	/**
	 *	Return a sorted map of all the TopProject-derived instances in the database.
	 *
	 *	@return	List of ICFIntTopProjectObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	List<ICFIntTopProjectObj> readAllTopProject( boolean forceRead );

	List<ICFIntTopProjectObj> readCachedAllTopProject();

	/**
	 *	Get the CFIntTopProjectObj instance for the primary key attributes.
	 *
	 *	@param	Id	The TopProject key attribute of the instance generating the id.
	 *
	 *	@return	CFIntTopProjectObj cached instance for the primary key, or
	 *		null if no such instance exists.
	 */
	ICFIntTopProjectObj readTopProjectByIdIdx( CFLibDbKeyHash256 Id );

	/**
	 *	Get the CFIntTopProjectObj instance for the primary key attributes.
	 *
	 *	@param	Id	The TopProject key attribute of the instance generating the id.
	 *
	 *	@return	CFIntTopProjectObj refreshed instance for the primary key, or
	 *		null if no such instance exists.
	 */
	ICFIntTopProjectObj readTopProjectByIdIdx( CFLibDbKeyHash256 Id,
		boolean forceRead );

	/**
	 *	Get the map of CFIntTopProjectObj instances sorted by their primary keys for the duplicate TenantIdx key.
	 *
	 *	@param	TenantId	The TopProject key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntTopProjectObj cached instances sorted by their primary keys for the duplicate TenantIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntTopProjectObj> readTopProjectByTenantIdx( CFLibDbKeyHash256 TenantId );

	/**
	 *	Get the map of CFIntTopProjectObj instances sorted by their primary keys for the duplicate TenantIdx key.
	 *
	 *	@param	TenantId	The TopProject key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntTopProjectObj cached instances sorted by their primary keys for the duplicate TenantIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntTopProjectObj> readTopProjectByTenantIdx( CFLibDbKeyHash256 TenantId,
		boolean forceRead );

	/**
	 *	Get the map of CFIntTopProjectObj instances sorted by their primary keys for the duplicate TopDomainIdx key.
	 *
	 *	@param	TopDomainId	The TopProject key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntTopProjectObj cached instances sorted by their primary keys for the duplicate TopDomainIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntTopProjectObj> readTopProjectByTopDomainIdx( CFLibDbKeyHash256 TopDomainId );

	/**
	 *	Get the map of CFIntTopProjectObj instances sorted by their primary keys for the duplicate TopDomainIdx key.
	 *
	 *	@param	TopDomainId	The TopProject key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntTopProjectObj cached instances sorted by their primary keys for the duplicate TopDomainIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntTopProjectObj> readTopProjectByTopDomainIdx( CFLibDbKeyHash256 TopDomainId,
		boolean forceRead );

	/**
	 *	Get the CFIntTopProjectObj instance for the unique NameIdx key.
	 *
	 *	@param	TopDomainId	The TopProject key attribute of the instance generating the id.
	 *
	 *	@param	Name	The TopProject key attribute of the instance generating the id.
	 *
	 *	@return	CFIntTopProjectObj cached instance for the unique NameIdx key, or
	 *		null if no such instance exists.
	 */
	ICFIntTopProjectObj readTopProjectByNameIdx(CFLibDbKeyHash256 TopDomainId,
		String Name );

	/**
	 *	Get the CFIntTopProjectObj instance for the unique NameIdx key.
	 *
	 *	@param	TopDomainId	The TopProject key attribute of the instance generating the id.
	 *
	 *	@param	Name	The TopProject key attribute of the instance generating the id.
	 *
	 *	@return	CFIntTopProjectObj refreshed instance for the unique NameIdx key, or
	 *		null if no such instance exists.
	 */
	ICFIntTopProjectObj readTopProjectByNameIdx(CFLibDbKeyHash256 TopDomainId,
		String Name,
		boolean forceRead );

	ICFIntTopProjectObj readCachedTopProjectByIdIdx( CFLibDbKeyHash256 Id );

	List<ICFIntTopProjectObj> readCachedTopProjectByTenantIdx( CFLibDbKeyHash256 TenantId );

	List<ICFIntTopProjectObj> readCachedTopProjectByTopDomainIdx( CFLibDbKeyHash256 TopDomainId );

	ICFIntTopProjectObj readCachedTopProjectByNameIdx( CFLibDbKeyHash256 TopDomainId,
		String Name );

	void deepDisposeTopProjectByIdIdx( CFLibDbKeyHash256 Id );

	void deepDisposeTopProjectByTenantIdx( CFLibDbKeyHash256 TenantId );

	void deepDisposeTopProjectByTopDomainIdx( CFLibDbKeyHash256 TopDomainId );

	void deepDisposeTopProjectByNameIdx( CFLibDbKeyHash256 TopDomainId,
		String Name );

	/**
	 *	Internal use only.
	 */
	ICFIntTopProjectObj updateTopProject( ICFIntTopProjectObj Obj );

	/**
	 *	Internal use only.
	 */
	void deleteTopProject( ICFIntTopProjectObj Obj );

	/**
	 *	Internal use only.
	 *
	 *	@param	Id	The TopProject key attribute of the instance generating the id.
	 */
	void deleteTopProjectByIdIdx( CFLibDbKeyHash256 Id );

	/**
	 *	Internal use only.
	 *
	 *	@param	TenantId	The TopProject key attribute of the instance generating the id.
	 */
	void deleteTopProjectByTenantIdx( CFLibDbKeyHash256 TenantId );

	/**
	 *	Internal use only.
	 *
	 *	@param	TopDomainId	The TopProject key attribute of the instance generating the id.
	 */
	void deleteTopProjectByTopDomainIdx( CFLibDbKeyHash256 TopDomainId );

	/**
	 *	Internal use only.
	 *
	 *	@param	TopDomainId	The TopProject key attribute of the instance generating the id.
	 *
	 *	@param	Name	The TopProject key attribute of the instance generating the id.
	 */
	void deleteTopProjectByNameIdx(CFLibDbKeyHash256 TopDomainId,
		String Name );
}
