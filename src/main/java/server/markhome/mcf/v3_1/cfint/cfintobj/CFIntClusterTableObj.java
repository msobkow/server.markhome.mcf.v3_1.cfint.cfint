// Description: Java 25 Table Object implementation for Cluster.

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

public class CFIntClusterTableObj
	implements ICFIntClusterTableObj
{
	protected ICFIntSchemaObj schema;
	private Map<CFLibDbKeyHash256, ICFSecClusterObj> members;
	private Map<CFLibDbKeyHash256, ICFSecClusterObj> allCluster;
	private Map< ICFSecClusterByUDomNameIdxKey,
		ICFSecClusterObj > indexByUDomNameIdx;
	private Map< ICFSecClusterByUDescrIdxKey,
		ICFSecClusterObj > indexByUDescrIdx;
	public static String TABLE_NAME = "Cluster";
	public static String TABLE_DBNAME = "clus";

	public CFIntClusterTableObj() {
		schema = null;
		members = new HashMap<CFLibDbKeyHash256, ICFSecClusterObj>();
		allCluster = null;
		indexByUDomNameIdx = null;
		indexByUDescrIdx = null;
	}

	public CFIntClusterTableObj( ICFSecSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<CFLibDbKeyHash256, ICFSecClusterObj>();
		allCluster = null;
		indexByUDomNameIdx = null;
		indexByUDescrIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFSecClusterTableObj.getRuntimeClassCode();
	}	

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	public static int getBackingClassCode() {
		return( CFSecClusterTableObj.getBackingClassCode() );
	}

	/**
	 *	Get the runtime class code for this table; this value is only stable after the application is fully initialized.
	 *
	 *	@return runtimeClassCode
	 */
	public static int getRuntimeClassCode() {
		return( CFSecClusterTableObj.getRuntimeClassCode() );
	}

	@Override
	public ICFSecSchemaObj getSchema() {
		return( schema );
	}

	@Override
	public void setSchema( ICFSecSchemaObj value ) {
		schema = (ICFIntSchemaObj)value;
	}

	@Override
	public String getTableName() {
		return( TABLE_NAME );
	}

	@Override
	public String getTableDbName() {
		return( TABLE_DBNAME );
	}

	@Override
	public Class getObjQualifyingClass() {
		return( null );
	}


	@Override
	public void minimizeMemory() {
		allCluster = null;
		indexByUDomNameIdx = null;
		indexByUDescrIdx = null;
		List<ICFSecClusterObj> toForget = new LinkedList<ICFSecClusterObj>();
		ICFSecClusterObj cur = null;
		Iterator<ICFSecClusterObj> iter = members.values().iterator();
		while( iter.hasNext() ) {
			cur = iter.next();
			toForget.add( cur );
		}
		iter = toForget.iterator();
		while( iter.hasNext() ) {
			cur = iter.next();
			cur.forget();
		}
	}
	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntClusterObj.
	 */
	@Override
	public ICFSecClusterObj newInstance() {
		ICFSecClusterObj inst = new CFIntClusterObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntClusterObj.
	 */
	@Override
	public ICFSecClusterEditObj newEditInstance( ICFSecClusterObj orig ) {
		ICFSecClusterEditObj edit = new CFIntClusterEditObj( orig );
		return( edit );
	}

	@Override
	public ICFSecClusterObj realiseCluster( ICFSecClusterObj Obj ) {
		ICFSecClusterObj obj = Obj;
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecClusterObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFSecClusterObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByUDomNameIdx != null ) {
				ICFSecClusterByUDomNameIdxKey keyUDomNameIdx =
					schema.getCFSecBackingStore().getFactoryCluster().newByUDomNameIdxKey();
				keyUDomNameIdx.setRequiredFullDomName( keepObj.getRequiredFullDomName() );
				indexByUDomNameIdx.remove( keyUDomNameIdx );
			}

			if( indexByUDescrIdx != null ) {
				ICFSecClusterByUDescrIdxKey keyUDescrIdx =
					schema.getCFSecBackingStore().getFactoryCluster().newByUDescrIdxKey();
				keyUDescrIdx.setRequiredDescription( keepObj.getRequiredDescription() );
				indexByUDescrIdx.remove( keyUDescrIdx );
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByUDomNameIdx != null ) {
				ICFSecClusterByUDomNameIdxKey keyUDomNameIdx =
					schema.getCFSecBackingStore().getFactoryCluster().newByUDomNameIdxKey();
				keyUDomNameIdx.setRequiredFullDomName( keepObj.getRequiredFullDomName() );
				indexByUDomNameIdx.put( keyUDomNameIdx, keepObj );
			}

			if( indexByUDescrIdx != null ) {
				ICFSecClusterByUDescrIdxKey keyUDescrIdx =
					schema.getCFSecBackingStore().getFactoryCluster().newByUDescrIdxKey();
				keyUDescrIdx.setRequiredDescription( keepObj.getRequiredDescription() );
				indexByUDescrIdx.put( keyUDescrIdx, keepObj );
			}

			if( allCluster != null ) {
				allCluster.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allCluster != null ) {
				allCluster.put( keepObj.getPKey(), keepObj );
			}

			if( indexByUDomNameIdx != null ) {
				ICFSecClusterByUDomNameIdxKey keyUDomNameIdx =
					schema.getCFSecBackingStore().getFactoryCluster().newByUDomNameIdxKey();
				keyUDomNameIdx.setRequiredFullDomName( keepObj.getRequiredFullDomName() );
				indexByUDomNameIdx.put( keyUDomNameIdx, keepObj );
			}

			if( indexByUDescrIdx != null ) {
				ICFSecClusterByUDescrIdxKey keyUDescrIdx =
					schema.getCFSecBackingStore().getFactoryCluster().newByUDescrIdxKey();
				keyUDescrIdx.setRequiredDescription( keepObj.getRequiredDescription() );
				indexByUDescrIdx.put( keyUDescrIdx, keepObj );
			}

		}
		return( keepObj );
	}

	@Override
	public ICFSecClusterObj createCluster( ICFSecClusterObj Obj ) {
		ICFSecClusterObj obj = Obj;
		ICFSecCluster rec = obj.getClusterRec();
		schema.getCFSecBackingStore().getTableCluster().createCluster(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFSecClusterObj readCluster( CFLibDbKeyHash256 pkey ) {
		return( readCluster( pkey, false ) );
	}

	@Override
	public ICFSecClusterObj readCluster( CFLibDbKeyHash256 pkey, boolean forceRead ) {
		ICFSecClusterObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFSecCluster readRec = schema.getCFSecBackingStore().getTableCluster().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getClusterTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFSecClusterObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecClusterObj readCachedCluster( CFLibDbKeyHash256 pkey ) {
		ICFSecClusterObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeCluster( ICFSecClusterObj obj )
	{
		final String S_ProcName = "CFIntClusterTableObj.reallyDeepDisposeCluster() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecClusterObj existing = readCachedCluster( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFSecClusterByUDomNameIdxKey keyUDomNameIdx = schema.getCFSecBackingStore().getFactoryCluster().newByUDomNameIdxKey();
		keyUDomNameIdx.setRequiredFullDomName( existing.getRequiredFullDomName() );

		ICFSecClusterByUDescrIdxKey keyUDescrIdx = schema.getCFSecBackingStore().getFactoryCluster().newByUDescrIdxKey();
		keyUDescrIdx.setRequiredDescription( existing.getRequiredDescription() );


		ICFSecSecGroupObj objDelSecGrpIncByGroup;
		List<ICFSecSecGroupObj> arrDelSecGrpIncByGroup = schema.getSecGroupTableObj().readCachedSecGroupByClusterIdx( existing.getRequiredId() );
		Iterator<ICFSecSecGroupObj> iterDelSecGrpIncByGroup = arrDelSecGrpIncByGroup.iterator();
		while( iterDelSecGrpIncByGroup.hasNext() ) {
			objDelSecGrpIncByGroup = iterDelSecGrpIncByGroup.next();
			if( objDelSecGrpIncByGroup != null ) {
						schema.getSecGrpIncTableObj().deepDisposeSecGrpIncByIncludeIdx( objDelSecGrpIncByGroup.getRequiredSecGroupId() );
			}
		}
		ICFSecSecGroupObj objDelSecGrpMembs;
		List<ICFSecSecGroupObj> arrDelSecGrpMembs = schema.getSecGroupTableObj().readCachedSecGroupByClusterIdx( existing.getRequiredId() );
		Iterator<ICFSecSecGroupObj> iterDelSecGrpMembs = arrDelSecGrpMembs.iterator();
		while( iterDelSecGrpMembs.hasNext() ) {
			objDelSecGrpMembs = iterDelSecGrpMembs.next();
			if( objDelSecGrpMembs != null ) {
						schema.getSecGrpMembTableObj().deepDisposeSecGrpMembByGroupIdx( objDelSecGrpMembs.getRequiredSecGroupId() );
			}
		}
		ICFSecSecGroupObj objDelSecGrpIncs;
		List<ICFSecSecGroupObj> arrDelSecGrpIncs = schema.getSecGroupTableObj().readCachedSecGroupByClusterIdx( existing.getRequiredId() );
		Iterator<ICFSecSecGroupObj> iterDelSecGrpIncs = arrDelSecGrpIncs.iterator();
		while( iterDelSecGrpIncs.hasNext() ) {
			objDelSecGrpIncs = iterDelSecGrpIncs.next();
			if( objDelSecGrpIncs != null ) {
						schema.getSecGrpIncTableObj().deepDisposeSecGrpIncByGroupIdx( objDelSecGrpIncs.getRequiredSecGroupId() );
			}
		}
					schema.getSecGroupTableObj().deepDisposeSecGroupByClusterIdx( existing.getRequiredId() );
					schema.getTenantTableObj().deepDisposeTenantByClusterIdx( existing.getRequiredId() );
					schema.getHostNodeTableObj().deepDisposeHostNodeByClusterIdx( existing.getRequiredId() );

		if( indexByUDomNameIdx != null ) {
			indexByUDomNameIdx.remove( keyUDomNameIdx );
		}

		if( indexByUDescrIdx != null ) {
			indexByUDescrIdx.remove( keyUDescrIdx );
		}


	}
	@Override
	public void deepDisposeCluster( CFLibDbKeyHash256 pkey ) {
		ICFSecClusterObj obj = readCachedCluster( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecClusterObj lockCluster( CFLibDbKeyHash256 pkey ) {
		ICFSecClusterObj locked = null;
		ICFSecCluster lockRec = schema.getCFSecBackingStore().getTableCluster().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getClusterTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFSecClusterObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockCluster", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFSecClusterObj> readAllCluster() {
		return( readAllCluster( false ) );
	}

	@Override
	public List<ICFSecClusterObj> readAllCluster( boolean forceRead ) {
		final String S_ProcName = "readAllCluster";
		if( ( allCluster == null ) || forceRead ) {
			Map<CFLibDbKeyHash256, ICFSecClusterObj> map = new HashMap<CFLibDbKeyHash256,ICFSecClusterObj>();
			allCluster = map;
			ICFSecCluster[] recList = schema.getCFSecBackingStore().getTableCluster().readAllDerived( null );
			ICFSecCluster rec;
			ICFSecClusterObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecClusterObj realised = (ICFSecClusterObj)obj.realise();
			}
		}
		int len = allCluster.size();
		ICFSecClusterObj arr[] = new ICFSecClusterObj[len];
		Iterator<ICFSecClusterObj> valIter = allCluster.values().iterator();
		int idx = 0;
		while( ( idx < len ) && valIter.hasNext() ) {
			arr[idx++] = valIter.next();
		}
		if( idx < len ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				S_ProcName,
				0,
				"idx",
				idx,
				len );
		}
		else if( valIter.hasNext() ) {
			throw new CFLibArgumentOverflowException( getClass(),
					S_ProcName,
					0,
					"idx",
					idx,
					len );
		}
		ArrayList<ICFSecClusterObj> arrayList = new ArrayList<ICFSecClusterObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecClusterObj> cmp = new Comparator<ICFSecClusterObj>() {
			@Override
			public int compare( ICFSecClusterObj lhs, ICFSecClusterObj rhs ) {
				if( lhs == null ) {
					if( rhs == null ) {
						return( 0 );
					}
					else {
						return( -1 );
					}
				}
				else if( rhs == null ) {
					return( 1 );
				}
				else {
					CFLibDbKeyHash256 lhsPKey = lhs.getPKey();
					CFLibDbKeyHash256 rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		List<ICFSecClusterObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecClusterObj> readCachedAllCluster() {
		final String S_ProcName = "readCachedAllCluster";
		ArrayList<ICFSecClusterObj> arrayList = new ArrayList<ICFSecClusterObj>();
		if( allCluster != null ) {
			int len = allCluster.size();
			ICFSecClusterObj arr[] = new ICFSecClusterObj[len];
			Iterator<ICFSecClusterObj> valIter = allCluster.values().iterator();
			int idx = 0;
			while( ( idx < len ) && valIter.hasNext() ) {
				arr[idx++] = valIter.next();
			}
			if( idx < len ) {
				throw new CFLibArgumentUnderflowException( getClass(),
					S_ProcName,
					0,
					"idx",
					idx,
					len );
			}
			else if( valIter.hasNext() ) {
				throw new CFLibArgumentOverflowException( getClass(),
						S_ProcName,
						0,
						"idx",
						idx,
						len );
			}
			for( idx = 0; idx < len; idx ++ ) {
				arrayList.add( arr[idx] );
			}
		}
		Comparator<ICFSecClusterObj> cmp = new Comparator<ICFSecClusterObj>() {
			public int compare( ICFSecClusterObj lhs, ICFSecClusterObj rhs ) {
				if( lhs == null ) {
					if( rhs == null ) {
						return( 0 );
					}
					else {
						return( -1 );
					}
				}
				else if( rhs == null ) {
					return( 1 );
				}
				else {
					CFLibDbKeyHash256 lhsPKey = lhs.getPKey();
					CFLibDbKeyHash256 rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		return( arrayList );
	}

	/**
	 *	Return a sorted map of a page of the Cluster-derived instances in the database.
	 *
	 *	@return	List of ICFSecClusterObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	@Override
	public List<ICFSecClusterObj> pageAllCluster(CFLibDbKeyHash256 priorId )
	{
		final String S_ProcName = "pageAllCluster";
		Map<CFLibDbKeyHash256, ICFSecClusterObj> map = new HashMap<CFLibDbKeyHash256,ICFSecClusterObj>();
		ICFSecCluster[] recList = schema.getCFSecBackingStore().getTableCluster().pageAllRec( null,
			priorId );
		ICFSecCluster rec;
		ICFSecClusterObj obj;
		ICFSecClusterObj realised;
		ArrayList<ICFSecClusterObj> arrayList = new ArrayList<ICFSecClusterObj>( recList.length );
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			realised = (ICFSecClusterObj)obj.realise();
			arrayList.add( realised );
		}
		return( arrayList );
	}

	@Override
	public ICFSecClusterObj readClusterByIdIdx( CFLibDbKeyHash256 Id )
	{
		return( readClusterByIdIdx( Id,
			false ) );
	}

	@Override
	public ICFSecClusterObj readClusterByIdIdx( CFLibDbKeyHash256 Id, boolean forceRead )
	{
		ICFSecClusterObj obj = readCluster( Id, forceRead );
		return( obj );
	}

	@Override
	public ICFSecClusterObj readClusterByUDomNameIdx( String FullDomName )
	{
		return( readClusterByUDomNameIdx( FullDomName,
			false ) );
	}

	@Override
	public ICFSecClusterObj readClusterByUDomNameIdx( String FullDomName, boolean forceRead )
	{
		if( indexByUDomNameIdx == null ) {
			indexByUDomNameIdx = new HashMap< ICFSecClusterByUDomNameIdxKey,
				ICFSecClusterObj >();
		}
		ICFSecClusterByUDomNameIdxKey key = schema.getCFSecBackingStore().getFactoryCluster().newByUDomNameIdxKey();
		key.setRequiredFullDomName( FullDomName );
		ICFSecClusterObj obj = null;
		if( ( ! forceRead ) && indexByUDomNameIdx.containsKey( key ) ) {
			obj = indexByUDomNameIdx.get( key );
		}
		else {
			ICFSecCluster rec = schema.getCFSecBackingStore().getTableCluster().readDerivedByUDomNameIdx( null,
				FullDomName );
			if( rec != null ) {
				obj = schema.getClusterTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFSecClusterObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecClusterObj readClusterByUDescrIdx( String Description )
	{
		return( readClusterByUDescrIdx( Description,
			false ) );
	}

	@Override
	public ICFSecClusterObj readClusterByUDescrIdx( String Description, boolean forceRead )
	{
		if( indexByUDescrIdx == null ) {
			indexByUDescrIdx = new HashMap< ICFSecClusterByUDescrIdxKey,
				ICFSecClusterObj >();
		}
		ICFSecClusterByUDescrIdxKey key = schema.getCFSecBackingStore().getFactoryCluster().newByUDescrIdxKey();
		key.setRequiredDescription( Description );
		ICFSecClusterObj obj = null;
		if( ( ! forceRead ) && indexByUDescrIdx.containsKey( key ) ) {
			obj = indexByUDescrIdx.get( key );
		}
		else {
			ICFSecCluster rec = schema.getCFSecBackingStore().getTableCluster().readDerivedByUDescrIdx( null,
				Description );
			if( rec != null ) {
				obj = schema.getClusterTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFSecClusterObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecClusterObj readCachedClusterByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFSecClusterObj obj = null;
		obj = readCachedCluster( Id );
		return( obj );
	}

	@Override
	public ICFSecClusterObj readCachedClusterByUDomNameIdx( String FullDomName )
	{
		ICFSecClusterObj obj = null;
		ICFSecClusterByUDomNameIdxKey key = schema.getCFSecBackingStore().getFactoryCluster().newByUDomNameIdxKey();
		key.setRequiredFullDomName( FullDomName );
		if( indexByUDomNameIdx != null ) {
			if( indexByUDomNameIdx.containsKey( key ) ) {
				obj = indexByUDomNameIdx.get( key );
			}
			else {
				Iterator<ICFSecClusterObj> valIter = members.values().iterator();
				while( ( obj == null ) && valIter.hasNext() ) {
					obj = valIter.next();
					if( obj != null ) {
						if( obj.getRec().compareTo( key ) != 0 ) {
							obj = null;
						}
					}
				}
			}
		}
		else {
			Iterator<ICFSecClusterObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) != 0 ) {
						obj = null;
					}
				}
			}
		}
		return( obj );
	}

	@Override
	public ICFSecClusterObj readCachedClusterByUDescrIdx( String Description )
	{
		ICFSecClusterObj obj = null;
		ICFSecClusterByUDescrIdxKey key = schema.getCFSecBackingStore().getFactoryCluster().newByUDescrIdxKey();
		key.setRequiredDescription( Description );
		if( indexByUDescrIdx != null ) {
			if( indexByUDescrIdx.containsKey( key ) ) {
				obj = indexByUDescrIdx.get( key );
			}
			else {
				Iterator<ICFSecClusterObj> valIter = members.values().iterator();
				while( ( obj == null ) && valIter.hasNext() ) {
					obj = valIter.next();
					if( obj != null ) {
						if( obj.getRec().compareTo( key ) != 0 ) {
							obj = null;
						}
					}
				}
			}
		}
		else {
			Iterator<ICFSecClusterObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) != 0 ) {
						obj = null;
					}
				}
			}
		}
		return( obj );
	}

	@Override
	public void deepDisposeClusterByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFSecClusterObj obj = readCachedClusterByIdIdx( Id );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeClusterByUDomNameIdx( String FullDomName )
	{
		ICFSecClusterObj obj = readCachedClusterByUDomNameIdx( FullDomName );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeClusterByUDescrIdx( String Description )
	{
		ICFSecClusterObj obj = readCachedClusterByUDescrIdx( Description );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecClusterObj updateCluster( ICFSecClusterObj Obj ) {
		ICFSecClusterObj obj = Obj;
		schema.getCFSecBackingStore().getTableCluster().updateCluster( null,
			Obj.getClusterRec() );
		obj = (ICFSecClusterObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteCluster( ICFSecClusterObj Obj ) {
		ICFSecClusterObj obj = Obj;
		schema.getCFSecBackingStore().getTableCluster().deleteCluster( null,
			obj.getClusterRec() );
		Obj.forget();
	}

	@Override
	public void deleteClusterByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFSecClusterObj obj = readCluster(Id);
		if( obj != null ) {
			ICFSecClusterEditObj editObj = (ICFSecClusterEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFSecClusterEditObj)obj.beginEdit();
				if( editObj != null ) {
					editStarted = true;
				}
				else {
					editStarted = false;
				}
			}
			else {
				editStarted = false;
			}
			if( editObj != null ) {
				editObj.deleteInstance();
				if( editStarted ) {
					editObj.endEdit();
				}
			}
			obj.forget();
		}
		deepDisposeClusterByIdIdx( Id );
	}

	@Override
	public void deleteClusterByUDomNameIdx( String FullDomName )
	{
		if( indexByUDomNameIdx == null ) {
			indexByUDomNameIdx = new HashMap< ICFSecClusterByUDomNameIdxKey,
				ICFSecClusterObj >();
		}
		ICFSecClusterByUDomNameIdxKey key = schema.getCFSecBackingStore().getFactoryCluster().newByUDomNameIdxKey();
		key.setRequiredFullDomName( FullDomName );
		ICFSecClusterObj obj = null;
		if( indexByUDomNameIdx.containsKey( key ) ) {
			obj = indexByUDomNameIdx.get( key );
			schema.getCFSecBackingStore().getTableCluster().deleteClusterByUDomNameIdx( null,
				FullDomName );
			obj.forget();
		}
		else {
			schema.getCFSecBackingStore().getTableCluster().deleteClusterByUDomNameIdx( null,
				FullDomName );
		}
		deepDisposeClusterByUDomNameIdx( FullDomName );
	}

	@Override
	public void deleteClusterByUDescrIdx( String Description )
	{
		if( indexByUDescrIdx == null ) {
			indexByUDescrIdx = new HashMap< ICFSecClusterByUDescrIdxKey,
				ICFSecClusterObj >();
		}
		ICFSecClusterByUDescrIdxKey key = schema.getCFSecBackingStore().getFactoryCluster().newByUDescrIdxKey();
		key.setRequiredDescription( Description );
		ICFSecClusterObj obj = null;
		if( indexByUDescrIdx.containsKey( key ) ) {
			obj = indexByUDescrIdx.get( key );
			schema.getCFSecBackingStore().getTableCluster().deleteClusterByUDescrIdx( null,
				Description );
			obj.forget();
		}
		else {
			schema.getCFSecBackingStore().getTableCluster().deleteClusterByUDescrIdx( null,
				Description );
		}
		deepDisposeClusterByUDescrIdx( Description );
	}

	public ICFSecClusterObj getSystemCluster() {
		ICFSecClusterObj clusterObj;
		clusterObj = readClusterByUDomNameIdx( "system" );
		if( clusterObj == null ) {
			clusterObj = newInstance();
			ICFSecClusterEditObj clusterEdit = clusterObj.beginEdit();
			clusterEdit.setRequiredFullDomName( "system" );
			clusterObj = clusterEdit.create();
			clusterEdit = null;
		}
		return( clusterObj );
	}
}