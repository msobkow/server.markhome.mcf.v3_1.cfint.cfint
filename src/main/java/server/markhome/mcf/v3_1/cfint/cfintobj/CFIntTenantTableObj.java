// Description: Java 25 Table Object implementation for Tenant.

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

public class CFIntTenantTableObj
	implements ICFIntTenantTableObj
{
	protected ICFIntSchemaObj schema;
	private Map<CFLibDbKeyHash256, ICFSecTenantObj> members;
	private Map<CFLibDbKeyHash256, ICFSecTenantObj> allTenant;
	private Map< ICFSecTenantByClusterIdxKey,
		Map<CFLibDbKeyHash256, ICFSecTenantObj > > indexByClusterIdx;
	private Map< ICFSecTenantByUNameIdxKey,
		ICFSecTenantObj > indexByUNameIdx;
	public static String TABLE_NAME = "Tenant";
	public static String TABLE_DBNAME = "tenant";

	public CFIntTenantTableObj() {
		schema = null;
		members = new HashMap<CFLibDbKeyHash256, ICFSecTenantObj>();
		allTenant = null;
		indexByClusterIdx = null;
		indexByUNameIdx = null;
	}

	public CFIntTenantTableObj( ICFSecSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<CFLibDbKeyHash256, ICFSecTenantObj>();
		allTenant = null;
		indexByClusterIdx = null;
		indexByUNameIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFSecTenantTableObj.getRuntimeClassCode();
	}	

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	public static int getBackingClassCode() {
		return( CFSecTenantTableObj.getBackingClassCode() );
	}

	/**
	 *	Get the runtime class code for this table; this value is only stable after the application is fully initialized.
	 *
	 *	@return runtimeClassCode
	 */
	public static int getRuntimeClassCode() {
		return( CFSecTenantTableObj.getRuntimeClassCode() );
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
		allTenant = null;
		indexByClusterIdx = null;
		indexByUNameIdx = null;
		List<ICFSecTenantObj> toForget = new LinkedList<ICFSecTenantObj>();
		ICFSecTenantObj cur = null;
		Iterator<ICFSecTenantObj> iter = members.values().iterator();
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
	 *	CFIntTenantObj.
	 */
	@Override
	public ICFSecTenantObj newInstance() {
		ICFSecTenantObj inst = new CFIntTenantObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntTenantObj.
	 */
	@Override
	public ICFSecTenantEditObj newEditInstance( ICFSecTenantObj orig ) {
		ICFSecTenantEditObj edit = new CFIntTenantEditObj( orig );
		return( edit );
	}

	@Override
	public ICFSecTenantObj realiseTenant( ICFSecTenantObj Obj ) {
		ICFSecTenantObj obj = Obj;
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecTenantObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFSecTenantObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByClusterIdx != null ) {
				ICFSecTenantByClusterIdxKey keyClusterIdx =
					schema.getCFSecBackingStore().getFactoryTenant().newByClusterIdxKey();
				keyClusterIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				Map<CFLibDbKeyHash256, ICFSecTenantObj > mapClusterIdx = indexByClusterIdx.get( keyClusterIdx );
				if( mapClusterIdx != null ) {
					mapClusterIdx.remove( keepObj.getPKey() );
					if( mapClusterIdx.size() <= 0 ) {
						indexByClusterIdx.remove( keyClusterIdx );
					}
				}
			}

			if( indexByUNameIdx != null ) {
				ICFSecTenantByUNameIdxKey keyUNameIdx =
					schema.getCFSecBackingStore().getFactoryTenant().newByUNameIdxKey();
				keyUNameIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyUNameIdx.setRequiredTenantName( keepObj.getRequiredTenantName() );
				indexByUNameIdx.remove( keyUNameIdx );
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByClusterIdx != null ) {
				ICFSecTenantByClusterIdxKey keyClusterIdx =
					schema.getCFSecBackingStore().getFactoryTenant().newByClusterIdxKey();
				keyClusterIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				Map<CFLibDbKeyHash256, ICFSecTenantObj > mapClusterIdx = indexByClusterIdx.get( keyClusterIdx );
				if( mapClusterIdx != null ) {
					mapClusterIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUNameIdx != null ) {
				ICFSecTenantByUNameIdxKey keyUNameIdx =
					schema.getCFSecBackingStore().getFactoryTenant().newByUNameIdxKey();
				keyUNameIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyUNameIdx.setRequiredTenantName( keepObj.getRequiredTenantName() );
				indexByUNameIdx.put( keyUNameIdx, keepObj );
			}

			if( allTenant != null ) {
				allTenant.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allTenant != null ) {
				allTenant.put( keepObj.getPKey(), keepObj );
			}

			if( indexByClusterIdx != null ) {
				ICFSecTenantByClusterIdxKey keyClusterIdx =
					schema.getCFSecBackingStore().getFactoryTenant().newByClusterIdxKey();
				keyClusterIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				Map<CFLibDbKeyHash256, ICFSecTenantObj > mapClusterIdx = indexByClusterIdx.get( keyClusterIdx );
				if( mapClusterIdx != null ) {
					mapClusterIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUNameIdx != null ) {
				ICFSecTenantByUNameIdxKey keyUNameIdx =
					schema.getCFSecBackingStore().getFactoryTenant().newByUNameIdxKey();
				keyUNameIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyUNameIdx.setRequiredTenantName( keepObj.getRequiredTenantName() );
				indexByUNameIdx.put( keyUNameIdx, keepObj );
			}

		}
		return( keepObj );
	}

	@Override
	public ICFSecTenantObj createTenant( ICFSecTenantObj Obj ) {
		ICFSecTenantObj obj = Obj;
		ICFSecTenant rec = obj.getTenantRec();
		schema.getCFSecBackingStore().getTableTenant().createTenant(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFSecTenantObj readTenant( CFLibDbKeyHash256 pkey ) {
		return( readTenant( pkey, false ) );
	}

	@Override
	public ICFSecTenantObj readTenant( CFLibDbKeyHash256 pkey, boolean forceRead ) {
		ICFSecTenantObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFSecTenant readRec = schema.getCFSecBackingStore().getTableTenant().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getTenantTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFSecTenantObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecTenantObj readCachedTenant( CFLibDbKeyHash256 pkey ) {
		ICFSecTenantObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeTenant( ICFSecTenantObj obj )
	{
		final String S_ProcName = "CFIntTenantTableObj.reallyDeepDisposeTenant() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecTenantObj existing = readCachedTenant( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFSecTenantByClusterIdxKey keyClusterIdx = schema.getCFSecBackingStore().getFactoryTenant().newByClusterIdxKey();
		keyClusterIdx.setRequiredClusterId( existing.getRequiredClusterId() );

		ICFSecTenantByUNameIdxKey keyUNameIdx = schema.getCFSecBackingStore().getFactoryTenant().newByUNameIdxKey();
		keyUNameIdx.setRequiredClusterId( existing.getRequiredClusterId() );
		keyUNameIdx.setRequiredTenantName( existing.getRequiredTenantName() );


					schema.getTldTableObj().deepDisposeTldByTenantIdx( existing.getRequiredId() );
		ICFSecTSecGroupObj objDelIncludedByGroup;
		List<ICFSecTSecGroupObj> arrDelIncludedByGroup = schema.getTSecGroupTableObj().readCachedTSecGroupByTenantIdx( existing.getRequiredId() );
		Iterator<ICFSecTSecGroupObj> iterDelIncludedByGroup = arrDelIncludedByGroup.iterator();
		while( iterDelIncludedByGroup.hasNext() ) {
			objDelIncludedByGroup = iterDelIncludedByGroup.next();
			if( objDelIncludedByGroup != null ) {
						schema.getTSecGrpIncTableObj().deepDisposeTSecGrpIncByIncludeIdx( objDelIncludedByGroup.getRequiredTSecGroupId() );
			}
		}
		ICFSecTSecGroupObj objDelGrpMembs;
		List<ICFSecTSecGroupObj> arrDelGrpMembs = schema.getTSecGroupTableObj().readCachedTSecGroupByTenantIdx( existing.getRequiredId() );
		Iterator<ICFSecTSecGroupObj> iterDelGrpMembs = arrDelGrpMembs.iterator();
		while( iterDelGrpMembs.hasNext() ) {
			objDelGrpMembs = iterDelGrpMembs.next();
			if( objDelGrpMembs != null ) {
						schema.getTSecGrpMembTableObj().deepDisposeTSecGrpMembByGroupIdx( objDelGrpMembs.getRequiredTSecGroupId() );
			}
		}
		ICFSecTSecGroupObj objDelGrpIncs;
		List<ICFSecTSecGroupObj> arrDelGrpIncs = schema.getTSecGroupTableObj().readCachedTSecGroupByTenantIdx( existing.getRequiredId() );
		Iterator<ICFSecTSecGroupObj> iterDelGrpIncs = arrDelGrpIncs.iterator();
		while( iterDelGrpIncs.hasNext() ) {
			objDelGrpIncs = iterDelGrpIncs.next();
			if( objDelGrpIncs != null ) {
						schema.getTSecGrpIncTableObj().deepDisposeTSecGrpIncByGroupIdx( objDelGrpIncs.getRequiredTSecGroupId() );
			}
		}
					schema.getTSecGroupTableObj().deepDisposeTSecGroupByTenantIdx( existing.getRequiredId() );

		if( indexByClusterIdx != null ) {
			if( indexByClusterIdx.containsKey( keyClusterIdx ) ) {
				indexByClusterIdx.get( keyClusterIdx ).remove( pkey );
				if( indexByClusterIdx.get( keyClusterIdx ).size() <= 0 ) {
					indexByClusterIdx.remove( keyClusterIdx );
				}
			}
		}

		if( indexByUNameIdx != null ) {
			indexByUNameIdx.remove( keyUNameIdx );
		}


	}
	@Override
	public void deepDisposeTenant( CFLibDbKeyHash256 pkey ) {
		ICFSecTenantObj obj = readCachedTenant( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecTenantObj lockTenant( CFLibDbKeyHash256 pkey ) {
		ICFSecTenantObj locked = null;
		ICFSecTenant lockRec = schema.getCFSecBackingStore().getTableTenant().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getTenantTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFSecTenantObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockTenant", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFSecTenantObj> readAllTenant() {
		return( readAllTenant( false ) );
	}

	@Override
	public List<ICFSecTenantObj> readAllTenant( boolean forceRead ) {
		final String S_ProcName = "readAllTenant";
		if( ( allTenant == null ) || forceRead ) {
			Map<CFLibDbKeyHash256, ICFSecTenantObj> map = new HashMap<CFLibDbKeyHash256,ICFSecTenantObj>();
			allTenant = map;
			ICFSecTenant[] recList = schema.getCFSecBackingStore().getTableTenant().readAllDerived( null );
			ICFSecTenant rec;
			ICFSecTenantObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecTenantObj realised = (ICFSecTenantObj)obj.realise();
			}
		}
		int len = allTenant.size();
		ICFSecTenantObj arr[] = new ICFSecTenantObj[len];
		Iterator<ICFSecTenantObj> valIter = allTenant.values().iterator();
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
		ArrayList<ICFSecTenantObj> arrayList = new ArrayList<ICFSecTenantObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecTenantObj> cmp = new Comparator<ICFSecTenantObj>() {
			@Override
			public int compare( ICFSecTenantObj lhs, ICFSecTenantObj rhs ) {
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
		List<ICFSecTenantObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecTenantObj> readCachedAllTenant() {
		final String S_ProcName = "readCachedAllTenant";
		ArrayList<ICFSecTenantObj> arrayList = new ArrayList<ICFSecTenantObj>();
		if( allTenant != null ) {
			int len = allTenant.size();
			ICFSecTenantObj arr[] = new ICFSecTenantObj[len];
			Iterator<ICFSecTenantObj> valIter = allTenant.values().iterator();
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
		Comparator<ICFSecTenantObj> cmp = new Comparator<ICFSecTenantObj>() {
			public int compare( ICFSecTenantObj lhs, ICFSecTenantObj rhs ) {
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
	 *	Return a sorted map of a page of the Tenant-derived instances in the database.
	 *
	 *	@return	List of ICFSecTenantObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	@Override
	public List<ICFSecTenantObj> pageAllTenant(CFLibDbKeyHash256 priorId )
	{
		final String S_ProcName = "pageAllTenant";
		Map<CFLibDbKeyHash256, ICFSecTenantObj> map = new HashMap<CFLibDbKeyHash256,ICFSecTenantObj>();
		ICFSecTenant[] recList = schema.getCFSecBackingStore().getTableTenant().pageAllRec( null,
			priorId );
		ICFSecTenant rec;
		ICFSecTenantObj obj;
		ICFSecTenantObj realised;
		ArrayList<ICFSecTenantObj> arrayList = new ArrayList<ICFSecTenantObj>( recList.length );
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			realised = (ICFSecTenantObj)obj.realise();
			arrayList.add( realised );
		}
		return( arrayList );
	}

	@Override
	public ICFSecTenantObj readTenantByIdIdx( CFLibDbKeyHash256 Id )
	{
		return( readTenantByIdIdx( Id,
			false ) );
	}

	@Override
	public ICFSecTenantObj readTenantByIdIdx( CFLibDbKeyHash256 Id, boolean forceRead )
	{
		ICFSecTenantObj obj = readTenant( Id, forceRead );
		return( obj );
	}

	@Override
	public List<ICFSecTenantObj> readTenantByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		return( readTenantByClusterIdx( ClusterId,
			false ) );
	}

	@Override
	public List<ICFSecTenantObj> readTenantByClusterIdx( CFLibDbKeyHash256 ClusterId,
		boolean forceRead )
	{
		final String S_ProcName = "readTenantByClusterIdx";
		ICFSecTenantByClusterIdxKey key = schema.getCFSecBackingStore().getFactoryTenant().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		Map<CFLibDbKeyHash256, ICFSecTenantObj> dict;
		if( indexByClusterIdx == null ) {
			indexByClusterIdx = new HashMap< ICFSecTenantByClusterIdxKey,
				Map< CFLibDbKeyHash256, ICFSecTenantObj > >();
		}
		if( ( ! forceRead ) && indexByClusterIdx.containsKey( key ) ) {
			dict = indexByClusterIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecTenantObj>();
			ICFSecTenantObj obj;
			ICFSecTenant[] recList = schema.getCFSecBackingStore().getTableTenant().readDerivedByClusterIdx( null,
				ClusterId );
			ICFSecTenant rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getTenantTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecTenantObj realised = (ICFSecTenantObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByClusterIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecTenantObj arr[] = new ICFSecTenantObj[len];
		Iterator<ICFSecTenantObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecTenantObj> arrayList = new ArrayList<ICFSecTenantObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecTenantObj> cmp = new Comparator<ICFSecTenantObj>() {
			@Override
			public int compare( ICFSecTenantObj lhs, ICFSecTenantObj rhs ) {
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
		List<ICFSecTenantObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFSecTenantObj readTenantByUNameIdx( CFLibDbKeyHash256 ClusterId,
		String TenantName )
	{
		return( readTenantByUNameIdx( ClusterId,
			TenantName,
			false ) );
	}

	@Override
	public ICFSecTenantObj readTenantByUNameIdx( CFLibDbKeyHash256 ClusterId,
		String TenantName, boolean forceRead )
	{
		if( indexByUNameIdx == null ) {
			indexByUNameIdx = new HashMap< ICFSecTenantByUNameIdxKey,
				ICFSecTenantObj >();
		}
		ICFSecTenantByUNameIdxKey key = schema.getCFSecBackingStore().getFactoryTenant().newByUNameIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredTenantName( TenantName );
		ICFSecTenantObj obj = null;
		if( ( ! forceRead ) && indexByUNameIdx.containsKey( key ) ) {
			obj = indexByUNameIdx.get( key );
		}
		else {
			ICFSecTenant rec = schema.getCFSecBackingStore().getTableTenant().readDerivedByUNameIdx( null,
				ClusterId,
				TenantName );
			if( rec != null ) {
				obj = schema.getTenantTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFSecTenantObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecTenantObj readCachedTenantByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFSecTenantObj obj = null;
		obj = readCachedTenant( Id );
		return( obj );
	}

	@Override
	public List<ICFSecTenantObj> readCachedTenantByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		final String S_ProcName = "readCachedTenantByClusterIdx";
		ICFSecTenantByClusterIdxKey key = schema.getCFSecBackingStore().getFactoryTenant().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		ArrayList<ICFSecTenantObj> arrayList = new ArrayList<ICFSecTenantObj>();
		if( indexByClusterIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecTenantObj> dict;
			if( indexByClusterIdx.containsKey( key ) ) {
				dict = indexByClusterIdx.get( key );
				int len = dict.size();
				ICFSecTenantObj arr[] = new ICFSecTenantObj[len];
				Iterator<ICFSecTenantObj> valIter = dict.values().iterator();
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
		}
		else {
			ICFSecTenantObj obj;
			Iterator<ICFSecTenantObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecTenantObj> cmp = new Comparator<ICFSecTenantObj>() {
			@Override
			public int compare( ICFSecTenantObj lhs, ICFSecTenantObj rhs ) {
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

	@Override
	public ICFSecTenantObj readCachedTenantByUNameIdx( CFLibDbKeyHash256 ClusterId,
		String TenantName )
	{
		ICFSecTenantObj obj = null;
		ICFSecTenantByUNameIdxKey key = schema.getCFSecBackingStore().getFactoryTenant().newByUNameIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredTenantName( TenantName );
		if( indexByUNameIdx != null ) {
			if( indexByUNameIdx.containsKey( key ) ) {
				obj = indexByUNameIdx.get( key );
			}
			else {
				Iterator<ICFSecTenantObj> valIter = members.values().iterator();
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
			Iterator<ICFSecTenantObj> valIter = members.values().iterator();
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
	public void deepDisposeTenantByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFSecTenantObj obj = readCachedTenantByIdIdx( Id );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeTenantByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		final String S_ProcName = "deepDisposeTenantByClusterIdx";
		ICFSecTenantObj obj;
		List<ICFSecTenantObj> arrayList = readCachedTenantByClusterIdx( ClusterId );
		if( arrayList != null )  {
			Iterator<ICFSecTenantObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeTenantByUNameIdx( CFLibDbKeyHash256 ClusterId,
		String TenantName )
	{
		ICFSecTenantObj obj = readCachedTenantByUNameIdx( ClusterId,
				TenantName );
		if( obj != null ) {
			obj.forget();
		}
	}

	/**
	 *	Read a page of data as a List of Tenant-derived instances sorted by their primary keys,
	 *	as identified by the duplicate ClusterIdx key attributes.
	 *
	 *	@param	ClusterId	The Tenant key attribute of the instance generating the id.
	 *
	 *	@return	A List of Tenant-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecTenantObj> pageTenantByClusterIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 priorId )
	{
		final String S_ProcName = "pageTenantByClusterIdx";
		ICFSecTenantByClusterIdxKey key = schema.getCFSecBackingStore().getFactoryTenant().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		List<ICFSecTenantObj> retList = new LinkedList<ICFSecTenantObj>();
		ICFSecTenantObj obj;
		ICFSecTenant[] recList = schema.getCFSecBackingStore().getTableTenant().pageRecByClusterIdx( null,
				ClusterId,
			priorId );
		ICFSecTenant rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getTenantTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecTenantObj realised = (ICFSecTenantObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	@Override
	public ICFSecTenantObj updateTenant( ICFSecTenantObj Obj ) {
		ICFSecTenantObj obj = Obj;
		schema.getCFSecBackingStore().getTableTenant().updateTenant( null,
			Obj.getTenantRec() );
		obj = (ICFSecTenantObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteTenant( ICFSecTenantObj Obj ) {
		ICFSecTenantObj obj = Obj;
		schema.getCFSecBackingStore().getTableTenant().deleteTenant( null,
			obj.getTenantRec() );
		Obj.forget();
	}

	@Override
	public void deleteTenantByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFSecTenantObj obj = readTenant(Id);
		if( obj != null ) {
			ICFSecTenantEditObj editObj = (ICFSecTenantEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFSecTenantEditObj)obj.beginEdit();
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
		deepDisposeTenantByIdIdx( Id );
	}

	@Override
	public void deleteTenantByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		ICFSecTenantByClusterIdxKey key = schema.getCFSecBackingStore().getFactoryTenant().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		if( indexByClusterIdx == null ) {
			indexByClusterIdx = new HashMap< ICFSecTenantByClusterIdxKey,
				Map< CFLibDbKeyHash256, ICFSecTenantObj > >();
		}
		if( indexByClusterIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecTenantObj> dict = indexByClusterIdx.get( key );
			schema.getCFSecBackingStore().getTableTenant().deleteTenantByClusterIdx( null,
				ClusterId );
			Iterator<ICFSecTenantObj> iter = dict.values().iterator();
			ICFSecTenantObj obj;
			List<ICFSecTenantObj> toForget = new LinkedList<ICFSecTenantObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByClusterIdx.remove( key );
		}
		else {
			schema.getCFSecBackingStore().getTableTenant().deleteTenantByClusterIdx( null,
				ClusterId );
		}
		deepDisposeTenantByClusterIdx( ClusterId );
	}

	@Override
	public void deleteTenantByUNameIdx( CFLibDbKeyHash256 ClusterId,
		String TenantName )
	{
		if( indexByUNameIdx == null ) {
			indexByUNameIdx = new HashMap< ICFSecTenantByUNameIdxKey,
				ICFSecTenantObj >();
		}
		ICFSecTenantByUNameIdxKey key = schema.getCFSecBackingStore().getFactoryTenant().newByUNameIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredTenantName( TenantName );
		ICFSecTenantObj obj = null;
		if( indexByUNameIdx.containsKey( key ) ) {
			obj = indexByUNameIdx.get( key );
			schema.getCFSecBackingStore().getTableTenant().deleteTenantByUNameIdx( null,
				ClusterId,
				TenantName );
			obj.forget();
		}
		else {
			schema.getCFSecBackingStore().getTableTenant().deleteTenantByUNameIdx( null,
				ClusterId,
				TenantName );
		}
		deepDisposeTenantByUNameIdx( ClusterId,
				TenantName );
	}

	public ICFSecTenantObj getSystemTenant() {
		ICFSecTenantObj tenantObj;
		ICFSecClusterObj clusterObj = schema.getClusterTableObj().getSystemCluster();
		tenantObj = readTenantByUNameIdx( clusterObj.getRequiredId(), "system" );
		if( tenantObj == null ) {
			tenantObj = newInstance();
			ICFSecTenantEditObj tenantEdit = tenantObj.beginEdit();
			tenantEdit.setRequiredContainerCluster( clusterObj );
			tenantEdit.setRequiredTenantName( "system" );
			tenantObj = tenantEdit.create();
			tenantEdit = null;
		}
		return( tenantObj );
	}
}