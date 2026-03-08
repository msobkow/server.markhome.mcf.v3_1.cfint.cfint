// Description: Java 25 Table Object implementation for SecGroup.

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

public class CFIntSecGroupTableObj
	implements ICFIntSecGroupTableObj
{
	protected ICFIntSchemaObj schema;
	private Map<CFLibDbKeyHash256, ICFSecSecGroupObj> members;
	private Map<CFLibDbKeyHash256, ICFSecSecGroupObj> allSecGroup;
	private Map< ICFSecSecGroupByClusterIdxKey,
		Map<CFLibDbKeyHash256, ICFSecSecGroupObj > > indexByClusterIdx;
	private Map< ICFSecSecGroupByClusterVisIdxKey,
		Map<CFLibDbKeyHash256, ICFSecSecGroupObj > > indexByClusterVisIdx;
	private Map< ICFSecSecGroupByUNameIdxKey,
		ICFSecSecGroupObj > indexByUNameIdx;
	public static String TABLE_NAME = "SecGroup";
	public static String TABLE_DBNAME = "secgrp";

	public CFIntSecGroupTableObj() {
		schema = null;
		members = new HashMap<CFLibDbKeyHash256, ICFSecSecGroupObj>();
		allSecGroup = null;
		indexByClusterIdx = null;
		indexByClusterVisIdx = null;
		indexByUNameIdx = null;
	}

	public CFIntSecGroupTableObj( ICFSecSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<CFLibDbKeyHash256, ICFSecSecGroupObj>();
		allSecGroup = null;
		indexByClusterIdx = null;
		indexByClusterVisIdx = null;
		indexByUNameIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFSecSecGroupTableObj.getRuntimeClassCode();
	}	

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	public static int getBackingClassCode() {
		return( CFSecSecGroupTableObj.getBackingClassCode() );
	}

	/**
	 *	Get the runtime class code for this table; this value is only stable after the application is fully initialized.
	 *
	 *	@return runtimeClassCode
	 */
	public static int getRuntimeClassCode() {
		return( CFSecSecGroupTableObj.getRuntimeClassCode() );
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
		allSecGroup = null;
		indexByClusterIdx = null;
		indexByClusterVisIdx = null;
		indexByUNameIdx = null;
		List<ICFSecSecGroupObj> toForget = new LinkedList<ICFSecSecGroupObj>();
		ICFSecSecGroupObj cur = null;
		Iterator<ICFSecSecGroupObj> iter = members.values().iterator();
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
	 *	CFIntSecGroupObj.
	 */
	@Override
	public ICFSecSecGroupObj newInstance() {
		ICFSecSecGroupObj inst = new CFIntSecGroupObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntSecGroupObj.
	 */
	@Override
	public ICFSecSecGroupEditObj newEditInstance( ICFSecSecGroupObj orig ) {
		ICFSecSecGroupEditObj edit = new CFIntSecGroupEditObj( orig );
		return( edit );
	}

	@Override
	public ICFSecSecGroupObj realiseSecGroup( ICFSecSecGroupObj Obj ) {
		ICFSecSecGroupObj obj = Obj;
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecSecGroupObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFSecSecGroupObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByClusterIdx != null ) {
				ICFSecSecGroupByClusterIdxKey keyClusterIdx =
					schema.getCFSecBackingStore().getFactorySecGroup().newByClusterIdxKey();
				keyClusterIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				Map<CFLibDbKeyHash256, ICFSecSecGroupObj > mapClusterIdx = indexByClusterIdx.get( keyClusterIdx );
				if( mapClusterIdx != null ) {
					mapClusterIdx.remove( keepObj.getPKey() );
					if( mapClusterIdx.size() <= 0 ) {
						indexByClusterIdx.remove( keyClusterIdx );
					}
				}
			}

			if( indexByClusterVisIdx != null ) {
				ICFSecSecGroupByClusterVisIdxKey keyClusterVisIdx =
					schema.getCFSecBackingStore().getFactorySecGroup().newByClusterVisIdxKey();
				keyClusterVisIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyClusterVisIdx.setRequiredIsVisible( keepObj.getRequiredIsVisible() );
				Map<CFLibDbKeyHash256, ICFSecSecGroupObj > mapClusterVisIdx = indexByClusterVisIdx.get( keyClusterVisIdx );
				if( mapClusterVisIdx != null ) {
					mapClusterVisIdx.remove( keepObj.getPKey() );
					if( mapClusterVisIdx.size() <= 0 ) {
						indexByClusterVisIdx.remove( keyClusterVisIdx );
					}
				}
			}

			if( indexByUNameIdx != null ) {
				ICFSecSecGroupByUNameIdxKey keyUNameIdx =
					schema.getCFSecBackingStore().getFactorySecGroup().newByUNameIdxKey();
				keyUNameIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyUNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByUNameIdx.remove( keyUNameIdx );
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByClusterIdx != null ) {
				ICFSecSecGroupByClusterIdxKey keyClusterIdx =
					schema.getCFSecBackingStore().getFactorySecGroup().newByClusterIdxKey();
				keyClusterIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				Map<CFLibDbKeyHash256, ICFSecSecGroupObj > mapClusterIdx = indexByClusterIdx.get( keyClusterIdx );
				if( mapClusterIdx != null ) {
					mapClusterIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByClusterVisIdx != null ) {
				ICFSecSecGroupByClusterVisIdxKey keyClusterVisIdx =
					schema.getCFSecBackingStore().getFactorySecGroup().newByClusterVisIdxKey();
				keyClusterVisIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyClusterVisIdx.setRequiredIsVisible( keepObj.getRequiredIsVisible() );
				Map<CFLibDbKeyHash256, ICFSecSecGroupObj > mapClusterVisIdx = indexByClusterVisIdx.get( keyClusterVisIdx );
				if( mapClusterVisIdx != null ) {
					mapClusterVisIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUNameIdx != null ) {
				ICFSecSecGroupByUNameIdxKey keyUNameIdx =
					schema.getCFSecBackingStore().getFactorySecGroup().newByUNameIdxKey();
				keyUNameIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyUNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByUNameIdx.put( keyUNameIdx, keepObj );
			}

			if( allSecGroup != null ) {
				allSecGroup.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allSecGroup != null ) {
				allSecGroup.put( keepObj.getPKey(), keepObj );
			}

			if( indexByClusterIdx != null ) {
				ICFSecSecGroupByClusterIdxKey keyClusterIdx =
					schema.getCFSecBackingStore().getFactorySecGroup().newByClusterIdxKey();
				keyClusterIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				Map<CFLibDbKeyHash256, ICFSecSecGroupObj > mapClusterIdx = indexByClusterIdx.get( keyClusterIdx );
				if( mapClusterIdx != null ) {
					mapClusterIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByClusterVisIdx != null ) {
				ICFSecSecGroupByClusterVisIdxKey keyClusterVisIdx =
					schema.getCFSecBackingStore().getFactorySecGroup().newByClusterVisIdxKey();
				keyClusterVisIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyClusterVisIdx.setRequiredIsVisible( keepObj.getRequiredIsVisible() );
				Map<CFLibDbKeyHash256, ICFSecSecGroupObj > mapClusterVisIdx = indexByClusterVisIdx.get( keyClusterVisIdx );
				if( mapClusterVisIdx != null ) {
					mapClusterVisIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUNameIdx != null ) {
				ICFSecSecGroupByUNameIdxKey keyUNameIdx =
					schema.getCFSecBackingStore().getFactorySecGroup().newByUNameIdxKey();
				keyUNameIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyUNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByUNameIdx.put( keyUNameIdx, keepObj );
			}

		}
		return( keepObj );
	}

	@Override
	public ICFSecSecGroupObj createSecGroup( ICFSecSecGroupObj Obj ) {
		ICFSecSecGroupObj obj = Obj;
		ICFSecSecGroup rec = obj.getSecGroupRec();
		schema.getCFSecBackingStore().getTableSecGroup().createSecGroup(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFSecSecGroupObj readSecGroup( CFLibDbKeyHash256 pkey ) {
		return( readSecGroup( pkey, false ) );
	}

	@Override
	public ICFSecSecGroupObj readSecGroup( CFLibDbKeyHash256 pkey, boolean forceRead ) {
		ICFSecSecGroupObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFSecSecGroup readRec = schema.getCFSecBackingStore().getTableSecGroup().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getSecGroupTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFSecSecGroupObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecSecGroupObj readCachedSecGroup( CFLibDbKeyHash256 pkey ) {
		ICFSecSecGroupObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeSecGroup( ICFSecSecGroupObj obj )
	{
		final String S_ProcName = "CFIntSecGroupTableObj.reallyDeepDisposeSecGroup() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecSecGroupObj existing = readCachedSecGroup( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFSecSecGroupByClusterIdxKey keyClusterIdx = schema.getCFSecBackingStore().getFactorySecGroup().newByClusterIdxKey();
		keyClusterIdx.setRequiredClusterId( existing.getRequiredClusterId() );

		ICFSecSecGroupByClusterVisIdxKey keyClusterVisIdx = schema.getCFSecBackingStore().getFactorySecGroup().newByClusterVisIdxKey();
		keyClusterVisIdx.setRequiredClusterId( existing.getRequiredClusterId() );
		keyClusterVisIdx.setRequiredIsVisible( existing.getRequiredIsVisible() );

		ICFSecSecGroupByUNameIdxKey keyUNameIdx = schema.getCFSecBackingStore().getFactorySecGroup().newByUNameIdxKey();
		keyUNameIdx.setRequiredClusterId( existing.getRequiredClusterId() );
		keyUNameIdx.setRequiredName( existing.getRequiredName() );


					schema.getSecGrpIncTableObj().deepDisposeSecGrpIncByIncludeIdx( existing.getRequiredSecGroupId() );
					schema.getSecGrpMembTableObj().deepDisposeSecGrpMembByGroupIdx( existing.getRequiredSecGroupId() );
					schema.getSecGrpIncTableObj().deepDisposeSecGrpIncByGroupIdx( existing.getRequiredSecGroupId() );

		if( indexByClusterIdx != null ) {
			if( indexByClusterIdx.containsKey( keyClusterIdx ) ) {
				indexByClusterIdx.get( keyClusterIdx ).remove( pkey );
				if( indexByClusterIdx.get( keyClusterIdx ).size() <= 0 ) {
					indexByClusterIdx.remove( keyClusterIdx );
				}
			}
		}

		if( indexByClusterVisIdx != null ) {
			if( indexByClusterVisIdx.containsKey( keyClusterVisIdx ) ) {
				indexByClusterVisIdx.get( keyClusterVisIdx ).remove( pkey );
				if( indexByClusterVisIdx.get( keyClusterVisIdx ).size() <= 0 ) {
					indexByClusterVisIdx.remove( keyClusterVisIdx );
				}
			}
		}

		if( indexByUNameIdx != null ) {
			indexByUNameIdx.remove( keyUNameIdx );
		}


	}
	@Override
	public void deepDisposeSecGroup( CFLibDbKeyHash256 pkey ) {
		ICFSecSecGroupObj obj = readCachedSecGroup( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecSecGroupObj lockSecGroup( CFLibDbKeyHash256 pkey ) {
		ICFSecSecGroupObj locked = null;
		ICFSecSecGroup lockRec = schema.getCFSecBackingStore().getTableSecGroup().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getSecGroupTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFSecSecGroupObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockSecGroup", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFSecSecGroupObj> readAllSecGroup() {
		return( readAllSecGroup( false ) );
	}

	@Override
	public List<ICFSecSecGroupObj> readAllSecGroup( boolean forceRead ) {
		final String S_ProcName = "readAllSecGroup";
		if( ( allSecGroup == null ) || forceRead ) {
			Map<CFLibDbKeyHash256, ICFSecSecGroupObj> map = new HashMap<CFLibDbKeyHash256,ICFSecSecGroupObj>();
			allSecGroup = map;
			ICFSecSecGroup[] recList = schema.getCFSecBackingStore().getTableSecGroup().readAllDerived( null );
			ICFSecSecGroup rec;
			ICFSecSecGroupObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecGroupObj realised = (ICFSecSecGroupObj)obj.realise();
			}
		}
		int len = allSecGroup.size();
		ICFSecSecGroupObj arr[] = new ICFSecSecGroupObj[len];
		Iterator<ICFSecSecGroupObj> valIter = allSecGroup.values().iterator();
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
		ArrayList<ICFSecSecGroupObj> arrayList = new ArrayList<ICFSecSecGroupObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecGroupObj> cmp = new Comparator<ICFSecSecGroupObj>() {
			@Override
			public int compare( ICFSecSecGroupObj lhs, ICFSecSecGroupObj rhs ) {
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
		List<ICFSecSecGroupObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecSecGroupObj> readCachedAllSecGroup() {
		final String S_ProcName = "readCachedAllSecGroup";
		ArrayList<ICFSecSecGroupObj> arrayList = new ArrayList<ICFSecSecGroupObj>();
		if( allSecGroup != null ) {
			int len = allSecGroup.size();
			ICFSecSecGroupObj arr[] = new ICFSecSecGroupObj[len];
			Iterator<ICFSecSecGroupObj> valIter = allSecGroup.values().iterator();
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
		Comparator<ICFSecSecGroupObj> cmp = new Comparator<ICFSecSecGroupObj>() {
			public int compare( ICFSecSecGroupObj lhs, ICFSecSecGroupObj rhs ) {
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
	public ICFSecSecGroupObj readSecGroupByIdIdx( CFLibDbKeyHash256 SecGroupId )
	{
		return( readSecGroupByIdIdx( SecGroupId,
			false ) );
	}

	@Override
	public ICFSecSecGroupObj readSecGroupByIdIdx( CFLibDbKeyHash256 SecGroupId, boolean forceRead )
	{
		ICFSecSecGroupObj obj = readSecGroup( SecGroupId, forceRead );
		return( obj );
	}

	@Override
	public List<ICFSecSecGroupObj> readSecGroupByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		return( readSecGroupByClusterIdx( ClusterId,
			false ) );
	}

	@Override
	public List<ICFSecSecGroupObj> readSecGroupByClusterIdx( CFLibDbKeyHash256 ClusterId,
		boolean forceRead )
	{
		final String S_ProcName = "readSecGroupByClusterIdx";
		ICFSecSecGroupByClusterIdxKey key = schema.getCFSecBackingStore().getFactorySecGroup().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		Map<CFLibDbKeyHash256, ICFSecSecGroupObj> dict;
		if( indexByClusterIdx == null ) {
			indexByClusterIdx = new HashMap< ICFSecSecGroupByClusterIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecGroupObj > >();
		}
		if( ( ! forceRead ) && indexByClusterIdx.containsKey( key ) ) {
			dict = indexByClusterIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecSecGroupObj>();
			ICFSecSecGroupObj obj;
			ICFSecSecGroup[] recList = schema.getCFSecBackingStore().getTableSecGroup().readDerivedByClusterIdx( null,
				ClusterId );
			ICFSecSecGroup rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getSecGroupTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecGroupObj realised = (ICFSecSecGroupObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByClusterIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecSecGroupObj arr[] = new ICFSecSecGroupObj[len];
		Iterator<ICFSecSecGroupObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecSecGroupObj> arrayList = new ArrayList<ICFSecSecGroupObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecGroupObj> cmp = new Comparator<ICFSecSecGroupObj>() {
			@Override
			public int compare( ICFSecSecGroupObj lhs, ICFSecSecGroupObj rhs ) {
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
		List<ICFSecSecGroupObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecSecGroupObj> readSecGroupByClusterVisIdx( CFLibDbKeyHash256 ClusterId,
		boolean IsVisible )
	{
		return( readSecGroupByClusterVisIdx( ClusterId,
			IsVisible,
			false ) );
	}

	@Override
	public List<ICFSecSecGroupObj> readSecGroupByClusterVisIdx( CFLibDbKeyHash256 ClusterId,
		boolean IsVisible,
		boolean forceRead )
	{
		final String S_ProcName = "readSecGroupByClusterVisIdx";
		ICFSecSecGroupByClusterVisIdxKey key = schema.getCFSecBackingStore().getFactorySecGroup().newByClusterVisIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredIsVisible( IsVisible );
		Map<CFLibDbKeyHash256, ICFSecSecGroupObj> dict;
		if( indexByClusterVisIdx == null ) {
			indexByClusterVisIdx = new HashMap< ICFSecSecGroupByClusterVisIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecGroupObj > >();
		}
		if( ( ! forceRead ) && indexByClusterVisIdx.containsKey( key ) ) {
			dict = indexByClusterVisIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecSecGroupObj>();
			ICFSecSecGroupObj obj;
			ICFSecSecGroup[] recList = schema.getCFSecBackingStore().getTableSecGroup().readDerivedByClusterVisIdx( null,
				ClusterId,
				IsVisible );
			ICFSecSecGroup rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getSecGroupTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecGroupObj realised = (ICFSecSecGroupObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByClusterVisIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecSecGroupObj arr[] = new ICFSecSecGroupObj[len];
		Iterator<ICFSecSecGroupObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecSecGroupObj> arrayList = new ArrayList<ICFSecSecGroupObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecGroupObj> cmp = new Comparator<ICFSecSecGroupObj>() {
			@Override
			public int compare( ICFSecSecGroupObj lhs, ICFSecSecGroupObj rhs ) {
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
		List<ICFSecSecGroupObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFSecSecGroupObj readSecGroupByUNameIdx( CFLibDbKeyHash256 ClusterId,
		String Name )
	{
		return( readSecGroupByUNameIdx( ClusterId,
			Name,
			false ) );
	}

	@Override
	public ICFSecSecGroupObj readSecGroupByUNameIdx( CFLibDbKeyHash256 ClusterId,
		String Name, boolean forceRead )
	{
		if( indexByUNameIdx == null ) {
			indexByUNameIdx = new HashMap< ICFSecSecGroupByUNameIdxKey,
				ICFSecSecGroupObj >();
		}
		ICFSecSecGroupByUNameIdxKey key = schema.getCFSecBackingStore().getFactorySecGroup().newByUNameIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredName( Name );
		ICFSecSecGroupObj obj = null;
		if( ( ! forceRead ) && indexByUNameIdx.containsKey( key ) ) {
			obj = indexByUNameIdx.get( key );
		}
		else {
			ICFSecSecGroup rec = schema.getCFSecBackingStore().getTableSecGroup().readDerivedByUNameIdx( null,
				ClusterId,
				Name );
			if( rec != null ) {
				obj = schema.getSecGroupTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFSecSecGroupObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecSecGroupObj readCachedSecGroupByIdIdx( CFLibDbKeyHash256 SecGroupId )
	{
		ICFSecSecGroupObj obj = null;
		obj = readCachedSecGroup( SecGroupId );
		return( obj );
	}

	@Override
	public List<ICFSecSecGroupObj> readCachedSecGroupByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		final String S_ProcName = "readCachedSecGroupByClusterIdx";
		ICFSecSecGroupByClusterIdxKey key = schema.getCFSecBackingStore().getFactorySecGroup().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		ArrayList<ICFSecSecGroupObj> arrayList = new ArrayList<ICFSecSecGroupObj>();
		if( indexByClusterIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecSecGroupObj> dict;
			if( indexByClusterIdx.containsKey( key ) ) {
				dict = indexByClusterIdx.get( key );
				int len = dict.size();
				ICFSecSecGroupObj arr[] = new ICFSecSecGroupObj[len];
				Iterator<ICFSecSecGroupObj> valIter = dict.values().iterator();
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
			ICFSecSecGroupObj obj;
			Iterator<ICFSecSecGroupObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecSecGroupObj> cmp = new Comparator<ICFSecSecGroupObj>() {
			@Override
			public int compare( ICFSecSecGroupObj lhs, ICFSecSecGroupObj rhs ) {
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
	public List<ICFSecSecGroupObj> readCachedSecGroupByClusterVisIdx( CFLibDbKeyHash256 ClusterId,
		boolean IsVisible )
	{
		final String S_ProcName = "readCachedSecGroupByClusterVisIdx";
		ICFSecSecGroupByClusterVisIdxKey key = schema.getCFSecBackingStore().getFactorySecGroup().newByClusterVisIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredIsVisible( IsVisible );
		ArrayList<ICFSecSecGroupObj> arrayList = new ArrayList<ICFSecSecGroupObj>();
		if( indexByClusterVisIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecSecGroupObj> dict;
			if( indexByClusterVisIdx.containsKey( key ) ) {
				dict = indexByClusterVisIdx.get( key );
				int len = dict.size();
				ICFSecSecGroupObj arr[] = new ICFSecSecGroupObj[len];
				Iterator<ICFSecSecGroupObj> valIter = dict.values().iterator();
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
			ICFSecSecGroupObj obj;
			Iterator<ICFSecSecGroupObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecSecGroupObj> cmp = new Comparator<ICFSecSecGroupObj>() {
			@Override
			public int compare( ICFSecSecGroupObj lhs, ICFSecSecGroupObj rhs ) {
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
	public ICFSecSecGroupObj readCachedSecGroupByUNameIdx( CFLibDbKeyHash256 ClusterId,
		String Name )
	{
		ICFSecSecGroupObj obj = null;
		ICFSecSecGroupByUNameIdxKey key = schema.getCFSecBackingStore().getFactorySecGroup().newByUNameIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredName( Name );
		if( indexByUNameIdx != null ) {
			if( indexByUNameIdx.containsKey( key ) ) {
				obj = indexByUNameIdx.get( key );
			}
			else {
				Iterator<ICFSecSecGroupObj> valIter = members.values().iterator();
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
			Iterator<ICFSecSecGroupObj> valIter = members.values().iterator();
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
	public void deepDisposeSecGroupByIdIdx( CFLibDbKeyHash256 SecGroupId )
	{
		ICFSecSecGroupObj obj = readCachedSecGroupByIdIdx( SecGroupId );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeSecGroupByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		final String S_ProcName = "deepDisposeSecGroupByClusterIdx";
		ICFSecSecGroupObj obj;
		List<ICFSecSecGroupObj> arrayList = readCachedSecGroupByClusterIdx( ClusterId );
		if( arrayList != null )  {
			Iterator<ICFSecSecGroupObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeSecGroupByClusterVisIdx( CFLibDbKeyHash256 ClusterId,
		boolean IsVisible )
	{
		final String S_ProcName = "deepDisposeSecGroupByClusterVisIdx";
		ICFSecSecGroupObj obj;
		List<ICFSecSecGroupObj> arrayList = readCachedSecGroupByClusterVisIdx( ClusterId,
				IsVisible );
		if( arrayList != null )  {
			Iterator<ICFSecSecGroupObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeSecGroupByUNameIdx( CFLibDbKeyHash256 ClusterId,
		String Name )
	{
		ICFSecSecGroupObj obj = readCachedSecGroupByUNameIdx( ClusterId,
				Name );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecSecGroupObj updateSecGroup( ICFSecSecGroupObj Obj ) {
		ICFSecSecGroupObj obj = Obj;
		schema.getCFSecBackingStore().getTableSecGroup().updateSecGroup( null,
			Obj.getSecGroupRec() );
		obj = (ICFSecSecGroupObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteSecGroup( ICFSecSecGroupObj Obj ) {
		ICFSecSecGroupObj obj = Obj;
		schema.getCFSecBackingStore().getTableSecGroup().deleteSecGroup( null,
			obj.getSecGroupRec() );
		Obj.forget();
	}

	@Override
	public void deleteSecGroupByIdIdx( CFLibDbKeyHash256 SecGroupId )
	{
		ICFSecSecGroupObj obj = readSecGroup(SecGroupId);
		if( obj != null ) {
			ICFSecSecGroupEditObj editObj = (ICFSecSecGroupEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFSecSecGroupEditObj)obj.beginEdit();
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
		deepDisposeSecGroupByIdIdx( SecGroupId );
	}

	@Override
	public void deleteSecGroupByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		ICFSecSecGroupByClusterIdxKey key = schema.getCFSecBackingStore().getFactorySecGroup().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		if( indexByClusterIdx == null ) {
			indexByClusterIdx = new HashMap< ICFSecSecGroupByClusterIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecGroupObj > >();
		}
		if( indexByClusterIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecSecGroupObj> dict = indexByClusterIdx.get( key );
			schema.getCFSecBackingStore().getTableSecGroup().deleteSecGroupByClusterIdx( null,
				ClusterId );
			Iterator<ICFSecSecGroupObj> iter = dict.values().iterator();
			ICFSecSecGroupObj obj;
			List<ICFSecSecGroupObj> toForget = new LinkedList<ICFSecSecGroupObj>();
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
			schema.getCFSecBackingStore().getTableSecGroup().deleteSecGroupByClusterIdx( null,
				ClusterId );
		}
		deepDisposeSecGroupByClusterIdx( ClusterId );
	}

	@Override
	public void deleteSecGroupByClusterVisIdx( CFLibDbKeyHash256 ClusterId,
		boolean IsVisible )
	{
		ICFSecSecGroupByClusterVisIdxKey key = schema.getCFSecBackingStore().getFactorySecGroup().newByClusterVisIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredIsVisible( IsVisible );
		if( indexByClusterVisIdx == null ) {
			indexByClusterVisIdx = new HashMap< ICFSecSecGroupByClusterVisIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecGroupObj > >();
		}
		if( indexByClusterVisIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecSecGroupObj> dict = indexByClusterVisIdx.get( key );
			schema.getCFSecBackingStore().getTableSecGroup().deleteSecGroupByClusterVisIdx( null,
				ClusterId,
				IsVisible );
			Iterator<ICFSecSecGroupObj> iter = dict.values().iterator();
			ICFSecSecGroupObj obj;
			List<ICFSecSecGroupObj> toForget = new LinkedList<ICFSecSecGroupObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByClusterVisIdx.remove( key );
		}
		else {
			schema.getCFSecBackingStore().getTableSecGroup().deleteSecGroupByClusterVisIdx( null,
				ClusterId,
				IsVisible );
		}
		deepDisposeSecGroupByClusterVisIdx( ClusterId,
				IsVisible );
	}

	@Override
	public void deleteSecGroupByUNameIdx( CFLibDbKeyHash256 ClusterId,
		String Name )
	{
		if( indexByUNameIdx == null ) {
			indexByUNameIdx = new HashMap< ICFSecSecGroupByUNameIdxKey,
				ICFSecSecGroupObj >();
		}
		ICFSecSecGroupByUNameIdxKey key = schema.getCFSecBackingStore().getFactorySecGroup().newByUNameIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredName( Name );
		ICFSecSecGroupObj obj = null;
		if( indexByUNameIdx.containsKey( key ) ) {
			obj = indexByUNameIdx.get( key );
			schema.getCFSecBackingStore().getTableSecGroup().deleteSecGroupByUNameIdx( null,
				ClusterId,
				Name );
			obj.forget();
		}
		else {
			schema.getCFSecBackingStore().getTableSecGroup().deleteSecGroupByUNameIdx( null,
				ClusterId,
				Name );
		}
		deepDisposeSecGroupByUNameIdx( ClusterId,
				Name );
	}
}