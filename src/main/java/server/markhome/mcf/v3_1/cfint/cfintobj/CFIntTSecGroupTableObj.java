// Description: Java 25 Table Object implementation for TSecGroup.

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

public class CFIntTSecGroupTableObj
	implements ICFIntTSecGroupTableObj
{
	protected ICFIntSchemaObj schema;
	private Map<CFLibDbKeyHash256, ICFSecTSecGroupObj> members;
	private Map<CFLibDbKeyHash256, ICFSecTSecGroupObj> allTSecGroup;
	private Map< ICFSecTSecGroupByTenantIdxKey,
		Map<CFLibDbKeyHash256, ICFSecTSecGroupObj > > indexByTenantIdx;
	private Map< ICFSecTSecGroupByTenantVisIdxKey,
		Map<CFLibDbKeyHash256, ICFSecTSecGroupObj > > indexByTenantVisIdx;
	private Map< ICFSecTSecGroupByUNameIdxKey,
		ICFSecTSecGroupObj > indexByUNameIdx;
	public static String TABLE_NAME = "TSecGroup";
	public static String TABLE_DBNAME = "tsecgrp";

	public CFIntTSecGroupTableObj() {
		schema = null;
		members = new HashMap<CFLibDbKeyHash256, ICFSecTSecGroupObj>();
		allTSecGroup = null;
		indexByTenantIdx = null;
		indexByTenantVisIdx = null;
		indexByUNameIdx = null;
	}

	public CFIntTSecGroupTableObj( ICFSecSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<CFLibDbKeyHash256, ICFSecTSecGroupObj>();
		allTSecGroup = null;
		indexByTenantIdx = null;
		indexByTenantVisIdx = null;
		indexByUNameIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFSecTSecGroupTableObj.getRuntimeClassCode();
	}	

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	public static int getBackingClassCode() {
		return( CFSecTSecGroupTableObj.getBackingClassCode() );
	}

	/**
	 *	Get the runtime class code for this table; this value is only stable after the application is fully initialized.
	 *
	 *	@return runtimeClassCode
	 */
	public static int getRuntimeClassCode() {
		return( CFSecTSecGroupTableObj.getRuntimeClassCode() );
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
		allTSecGroup = null;
		indexByTenantIdx = null;
		indexByTenantVisIdx = null;
		indexByUNameIdx = null;
		List<ICFSecTSecGroupObj> toForget = new LinkedList<ICFSecTSecGroupObj>();
		ICFSecTSecGroupObj cur = null;
		Iterator<ICFSecTSecGroupObj> iter = members.values().iterator();
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
	 *	CFIntTSecGroupObj.
	 */
	@Override
	public ICFSecTSecGroupObj newInstance() {
		ICFSecTSecGroupObj inst = new CFIntTSecGroupObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntTSecGroupObj.
	 */
	@Override
	public ICFSecTSecGroupEditObj newEditInstance( ICFSecTSecGroupObj orig ) {
		ICFSecTSecGroupEditObj edit = new CFIntTSecGroupEditObj( orig );
		return( edit );
	}

	@Override
	public ICFSecTSecGroupObj realiseTSecGroup( ICFSecTSecGroupObj Obj ) {
		ICFSecTSecGroupObj obj = Obj;
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecTSecGroupObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFSecTSecGroupObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByTenantIdx != null ) {
				ICFSecTSecGroupByTenantIdxKey keyTenantIdx =
					schema.getCFSecBackingStore().getFactoryTSecGroup().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFSecTSecGroupObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.remove( keepObj.getPKey() );
					if( mapTenantIdx.size() <= 0 ) {
						indexByTenantIdx.remove( keyTenantIdx );
					}
				}
			}

			if( indexByTenantVisIdx != null ) {
				ICFSecTSecGroupByTenantVisIdxKey keyTenantVisIdx =
					schema.getCFSecBackingStore().getFactoryTSecGroup().newByTenantVisIdxKey();
				keyTenantVisIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				keyTenantVisIdx.setRequiredIsVisible( keepObj.getRequiredIsVisible() );
				Map<CFLibDbKeyHash256, ICFSecTSecGroupObj > mapTenantVisIdx = indexByTenantVisIdx.get( keyTenantVisIdx );
				if( mapTenantVisIdx != null ) {
					mapTenantVisIdx.remove( keepObj.getPKey() );
					if( mapTenantVisIdx.size() <= 0 ) {
						indexByTenantVisIdx.remove( keyTenantVisIdx );
					}
				}
			}

			if( indexByUNameIdx != null ) {
				ICFSecTSecGroupByUNameIdxKey keyUNameIdx =
					schema.getCFSecBackingStore().getFactoryTSecGroup().newByUNameIdxKey();
				keyUNameIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				keyUNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByUNameIdx.remove( keyUNameIdx );
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByTenantIdx != null ) {
				ICFSecTSecGroupByTenantIdxKey keyTenantIdx =
					schema.getCFSecBackingStore().getFactoryTSecGroup().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFSecTSecGroupObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByTenantVisIdx != null ) {
				ICFSecTSecGroupByTenantVisIdxKey keyTenantVisIdx =
					schema.getCFSecBackingStore().getFactoryTSecGroup().newByTenantVisIdxKey();
				keyTenantVisIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				keyTenantVisIdx.setRequiredIsVisible( keepObj.getRequiredIsVisible() );
				Map<CFLibDbKeyHash256, ICFSecTSecGroupObj > mapTenantVisIdx = indexByTenantVisIdx.get( keyTenantVisIdx );
				if( mapTenantVisIdx != null ) {
					mapTenantVisIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUNameIdx != null ) {
				ICFSecTSecGroupByUNameIdxKey keyUNameIdx =
					schema.getCFSecBackingStore().getFactoryTSecGroup().newByUNameIdxKey();
				keyUNameIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				keyUNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByUNameIdx.put( keyUNameIdx, keepObj );
			}

			if( allTSecGroup != null ) {
				allTSecGroup.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allTSecGroup != null ) {
				allTSecGroup.put( keepObj.getPKey(), keepObj );
			}

			if( indexByTenantIdx != null ) {
				ICFSecTSecGroupByTenantIdxKey keyTenantIdx =
					schema.getCFSecBackingStore().getFactoryTSecGroup().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFSecTSecGroupObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByTenantVisIdx != null ) {
				ICFSecTSecGroupByTenantVisIdxKey keyTenantVisIdx =
					schema.getCFSecBackingStore().getFactoryTSecGroup().newByTenantVisIdxKey();
				keyTenantVisIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				keyTenantVisIdx.setRequiredIsVisible( keepObj.getRequiredIsVisible() );
				Map<CFLibDbKeyHash256, ICFSecTSecGroupObj > mapTenantVisIdx = indexByTenantVisIdx.get( keyTenantVisIdx );
				if( mapTenantVisIdx != null ) {
					mapTenantVisIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUNameIdx != null ) {
				ICFSecTSecGroupByUNameIdxKey keyUNameIdx =
					schema.getCFSecBackingStore().getFactoryTSecGroup().newByUNameIdxKey();
				keyUNameIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				keyUNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByUNameIdx.put( keyUNameIdx, keepObj );
			}

		}
		return( keepObj );
	}

	@Override
	public ICFSecTSecGroupObj createTSecGroup( ICFSecTSecGroupObj Obj ) {
		ICFSecTSecGroupObj obj = Obj;
		ICFSecTSecGroup rec = obj.getTSecGroupRec();
		schema.getCFSecBackingStore().getTableTSecGroup().createTSecGroup(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFSecTSecGroupObj readTSecGroup( CFLibDbKeyHash256 pkey ) {
		return( readTSecGroup( pkey, false ) );
	}

	@Override
	public ICFSecTSecGroupObj readTSecGroup( CFLibDbKeyHash256 pkey, boolean forceRead ) {
		ICFSecTSecGroupObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFSecTSecGroup readRec = schema.getCFSecBackingStore().getTableTSecGroup().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getTSecGroupTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFSecTSecGroupObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecTSecGroupObj readCachedTSecGroup( CFLibDbKeyHash256 pkey ) {
		ICFSecTSecGroupObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeTSecGroup( ICFSecTSecGroupObj obj )
	{
		final String S_ProcName = "CFIntTSecGroupTableObj.reallyDeepDisposeTSecGroup() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecTSecGroupObj existing = readCachedTSecGroup( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFSecTSecGroupByTenantIdxKey keyTenantIdx = schema.getCFSecBackingStore().getFactoryTSecGroup().newByTenantIdxKey();
		keyTenantIdx.setRequiredTenantId( existing.getRequiredTenantId() );

		ICFSecTSecGroupByTenantVisIdxKey keyTenantVisIdx = schema.getCFSecBackingStore().getFactoryTSecGroup().newByTenantVisIdxKey();
		keyTenantVisIdx.setRequiredTenantId( existing.getRequiredTenantId() );
		keyTenantVisIdx.setRequiredIsVisible( existing.getRequiredIsVisible() );

		ICFSecTSecGroupByUNameIdxKey keyUNameIdx = schema.getCFSecBackingStore().getFactoryTSecGroup().newByUNameIdxKey();
		keyUNameIdx.setRequiredTenantId( existing.getRequiredTenantId() );
		keyUNameIdx.setRequiredName( existing.getRequiredName() );


					schema.getTSecGrpIncTableObj().deepDisposeTSecGrpIncByIncludeIdx( existing.getRequiredTSecGroupId() );
					schema.getTSecGrpMembTableObj().deepDisposeTSecGrpMembByGroupIdx( existing.getRequiredTSecGroupId() );
					schema.getTSecGrpIncTableObj().deepDisposeTSecGrpIncByGroupIdx( existing.getRequiredTSecGroupId() );

		if( indexByTenantIdx != null ) {
			if( indexByTenantIdx.containsKey( keyTenantIdx ) ) {
				indexByTenantIdx.get( keyTenantIdx ).remove( pkey );
				if( indexByTenantIdx.get( keyTenantIdx ).size() <= 0 ) {
					indexByTenantIdx.remove( keyTenantIdx );
				}
			}
		}

		if( indexByTenantVisIdx != null ) {
			if( indexByTenantVisIdx.containsKey( keyTenantVisIdx ) ) {
				indexByTenantVisIdx.get( keyTenantVisIdx ).remove( pkey );
				if( indexByTenantVisIdx.get( keyTenantVisIdx ).size() <= 0 ) {
					indexByTenantVisIdx.remove( keyTenantVisIdx );
				}
			}
		}

		if( indexByUNameIdx != null ) {
			indexByUNameIdx.remove( keyUNameIdx );
		}


	}
	@Override
	public void deepDisposeTSecGroup( CFLibDbKeyHash256 pkey ) {
		ICFSecTSecGroupObj obj = readCachedTSecGroup( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecTSecGroupObj lockTSecGroup( CFLibDbKeyHash256 pkey ) {
		ICFSecTSecGroupObj locked = null;
		ICFSecTSecGroup lockRec = schema.getCFSecBackingStore().getTableTSecGroup().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getTSecGroupTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFSecTSecGroupObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockTSecGroup", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFSecTSecGroupObj> readAllTSecGroup() {
		return( readAllTSecGroup( false ) );
	}

	@Override
	public List<ICFSecTSecGroupObj> readAllTSecGroup( boolean forceRead ) {
		final String S_ProcName = "readAllTSecGroup";
		if( ( allTSecGroup == null ) || forceRead ) {
			Map<CFLibDbKeyHash256, ICFSecTSecGroupObj> map = new HashMap<CFLibDbKeyHash256,ICFSecTSecGroupObj>();
			allTSecGroup = map;
			ICFSecTSecGroup[] recList = schema.getCFSecBackingStore().getTableTSecGroup().readAllDerived( null );
			ICFSecTSecGroup rec;
			ICFSecTSecGroupObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecTSecGroupObj realised = (ICFSecTSecGroupObj)obj.realise();
			}
		}
		int len = allTSecGroup.size();
		ICFSecTSecGroupObj arr[] = new ICFSecTSecGroupObj[len];
		Iterator<ICFSecTSecGroupObj> valIter = allTSecGroup.values().iterator();
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
		ArrayList<ICFSecTSecGroupObj> arrayList = new ArrayList<ICFSecTSecGroupObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecTSecGroupObj> cmp = new Comparator<ICFSecTSecGroupObj>() {
			@Override
			public int compare( ICFSecTSecGroupObj lhs, ICFSecTSecGroupObj rhs ) {
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
		List<ICFSecTSecGroupObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecTSecGroupObj> readCachedAllTSecGroup() {
		final String S_ProcName = "readCachedAllTSecGroup";
		ArrayList<ICFSecTSecGroupObj> arrayList = new ArrayList<ICFSecTSecGroupObj>();
		if( allTSecGroup != null ) {
			int len = allTSecGroup.size();
			ICFSecTSecGroupObj arr[] = new ICFSecTSecGroupObj[len];
			Iterator<ICFSecTSecGroupObj> valIter = allTSecGroup.values().iterator();
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
		Comparator<ICFSecTSecGroupObj> cmp = new Comparator<ICFSecTSecGroupObj>() {
			public int compare( ICFSecTSecGroupObj lhs, ICFSecTSecGroupObj rhs ) {
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
	public ICFSecTSecGroupObj readTSecGroupByIdIdx( CFLibDbKeyHash256 TSecGroupId )
	{
		return( readTSecGroupByIdIdx( TSecGroupId,
			false ) );
	}

	@Override
	public ICFSecTSecGroupObj readTSecGroupByIdIdx( CFLibDbKeyHash256 TSecGroupId, boolean forceRead )
	{
		ICFSecTSecGroupObj obj = readTSecGroup( TSecGroupId, forceRead );
		return( obj );
	}

	@Override
	public List<ICFSecTSecGroupObj> readTSecGroupByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		return( readTSecGroupByTenantIdx( TenantId,
			false ) );
	}

	@Override
	public List<ICFSecTSecGroupObj> readTSecGroupByTenantIdx( CFLibDbKeyHash256 TenantId,
		boolean forceRead )
	{
		final String S_ProcName = "readTSecGroupByTenantIdx";
		ICFSecTSecGroupByTenantIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGroup().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		Map<CFLibDbKeyHash256, ICFSecTSecGroupObj> dict;
		if( indexByTenantIdx == null ) {
			indexByTenantIdx = new HashMap< ICFSecTSecGroupByTenantIdxKey,
				Map< CFLibDbKeyHash256, ICFSecTSecGroupObj > >();
		}
		if( ( ! forceRead ) && indexByTenantIdx.containsKey( key ) ) {
			dict = indexByTenantIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecTSecGroupObj>();
			ICFSecTSecGroupObj obj;
			ICFSecTSecGroup[] recList = schema.getCFSecBackingStore().getTableTSecGroup().readDerivedByTenantIdx( null,
				TenantId );
			ICFSecTSecGroup rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getTSecGroupTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecTSecGroupObj realised = (ICFSecTSecGroupObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByTenantIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecTSecGroupObj arr[] = new ICFSecTSecGroupObj[len];
		Iterator<ICFSecTSecGroupObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecTSecGroupObj> arrayList = new ArrayList<ICFSecTSecGroupObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecTSecGroupObj> cmp = new Comparator<ICFSecTSecGroupObj>() {
			@Override
			public int compare( ICFSecTSecGroupObj lhs, ICFSecTSecGroupObj rhs ) {
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
		List<ICFSecTSecGroupObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecTSecGroupObj> readTSecGroupByTenantVisIdx( CFLibDbKeyHash256 TenantId,
		boolean IsVisible )
	{
		return( readTSecGroupByTenantVisIdx( TenantId,
			IsVisible,
			false ) );
	}

	@Override
	public List<ICFSecTSecGroupObj> readTSecGroupByTenantVisIdx( CFLibDbKeyHash256 TenantId,
		boolean IsVisible,
		boolean forceRead )
	{
		final String S_ProcName = "readTSecGroupByTenantVisIdx";
		ICFSecTSecGroupByTenantVisIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGroup().newByTenantVisIdxKey();
		key.setRequiredTenantId( TenantId );
		key.setRequiredIsVisible( IsVisible );
		Map<CFLibDbKeyHash256, ICFSecTSecGroupObj> dict;
		if( indexByTenantVisIdx == null ) {
			indexByTenantVisIdx = new HashMap< ICFSecTSecGroupByTenantVisIdxKey,
				Map< CFLibDbKeyHash256, ICFSecTSecGroupObj > >();
		}
		if( ( ! forceRead ) && indexByTenantVisIdx.containsKey( key ) ) {
			dict = indexByTenantVisIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecTSecGroupObj>();
			ICFSecTSecGroupObj obj;
			ICFSecTSecGroup[] recList = schema.getCFSecBackingStore().getTableTSecGroup().readDerivedByTenantVisIdx( null,
				TenantId,
				IsVisible );
			ICFSecTSecGroup rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getTSecGroupTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecTSecGroupObj realised = (ICFSecTSecGroupObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByTenantVisIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecTSecGroupObj arr[] = new ICFSecTSecGroupObj[len];
		Iterator<ICFSecTSecGroupObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecTSecGroupObj> arrayList = new ArrayList<ICFSecTSecGroupObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecTSecGroupObj> cmp = new Comparator<ICFSecTSecGroupObj>() {
			@Override
			public int compare( ICFSecTSecGroupObj lhs, ICFSecTSecGroupObj rhs ) {
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
		List<ICFSecTSecGroupObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFSecTSecGroupObj readTSecGroupByUNameIdx( CFLibDbKeyHash256 TenantId,
		String Name )
	{
		return( readTSecGroupByUNameIdx( TenantId,
			Name,
			false ) );
	}

	@Override
	public ICFSecTSecGroupObj readTSecGroupByUNameIdx( CFLibDbKeyHash256 TenantId,
		String Name, boolean forceRead )
	{
		if( indexByUNameIdx == null ) {
			indexByUNameIdx = new HashMap< ICFSecTSecGroupByUNameIdxKey,
				ICFSecTSecGroupObj >();
		}
		ICFSecTSecGroupByUNameIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGroup().newByUNameIdxKey();
		key.setRequiredTenantId( TenantId );
		key.setRequiredName( Name );
		ICFSecTSecGroupObj obj = null;
		if( ( ! forceRead ) && indexByUNameIdx.containsKey( key ) ) {
			obj = indexByUNameIdx.get( key );
		}
		else {
			ICFSecTSecGroup rec = schema.getCFSecBackingStore().getTableTSecGroup().readDerivedByUNameIdx( null,
				TenantId,
				Name );
			if( rec != null ) {
				obj = schema.getTSecGroupTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFSecTSecGroupObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecTSecGroupObj readCachedTSecGroupByIdIdx( CFLibDbKeyHash256 TSecGroupId )
	{
		ICFSecTSecGroupObj obj = null;
		obj = readCachedTSecGroup( TSecGroupId );
		return( obj );
	}

	@Override
	public List<ICFSecTSecGroupObj> readCachedTSecGroupByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		final String S_ProcName = "readCachedTSecGroupByTenantIdx";
		ICFSecTSecGroupByTenantIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGroup().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		ArrayList<ICFSecTSecGroupObj> arrayList = new ArrayList<ICFSecTSecGroupObj>();
		if( indexByTenantIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecTSecGroupObj> dict;
			if( indexByTenantIdx.containsKey( key ) ) {
				dict = indexByTenantIdx.get( key );
				int len = dict.size();
				ICFSecTSecGroupObj arr[] = new ICFSecTSecGroupObj[len];
				Iterator<ICFSecTSecGroupObj> valIter = dict.values().iterator();
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
			ICFSecTSecGroupObj obj;
			Iterator<ICFSecTSecGroupObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecTSecGroupObj> cmp = new Comparator<ICFSecTSecGroupObj>() {
			@Override
			public int compare( ICFSecTSecGroupObj lhs, ICFSecTSecGroupObj rhs ) {
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
	public List<ICFSecTSecGroupObj> readCachedTSecGroupByTenantVisIdx( CFLibDbKeyHash256 TenantId,
		boolean IsVisible )
	{
		final String S_ProcName = "readCachedTSecGroupByTenantVisIdx";
		ICFSecTSecGroupByTenantVisIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGroup().newByTenantVisIdxKey();
		key.setRequiredTenantId( TenantId );
		key.setRequiredIsVisible( IsVisible );
		ArrayList<ICFSecTSecGroupObj> arrayList = new ArrayList<ICFSecTSecGroupObj>();
		if( indexByTenantVisIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecTSecGroupObj> dict;
			if( indexByTenantVisIdx.containsKey( key ) ) {
				dict = indexByTenantVisIdx.get( key );
				int len = dict.size();
				ICFSecTSecGroupObj arr[] = new ICFSecTSecGroupObj[len];
				Iterator<ICFSecTSecGroupObj> valIter = dict.values().iterator();
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
			ICFSecTSecGroupObj obj;
			Iterator<ICFSecTSecGroupObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecTSecGroupObj> cmp = new Comparator<ICFSecTSecGroupObj>() {
			@Override
			public int compare( ICFSecTSecGroupObj lhs, ICFSecTSecGroupObj rhs ) {
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
	public ICFSecTSecGroupObj readCachedTSecGroupByUNameIdx( CFLibDbKeyHash256 TenantId,
		String Name )
	{
		ICFSecTSecGroupObj obj = null;
		ICFSecTSecGroupByUNameIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGroup().newByUNameIdxKey();
		key.setRequiredTenantId( TenantId );
		key.setRequiredName( Name );
		if( indexByUNameIdx != null ) {
			if( indexByUNameIdx.containsKey( key ) ) {
				obj = indexByUNameIdx.get( key );
			}
			else {
				Iterator<ICFSecTSecGroupObj> valIter = members.values().iterator();
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
			Iterator<ICFSecTSecGroupObj> valIter = members.values().iterator();
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
	public void deepDisposeTSecGroupByIdIdx( CFLibDbKeyHash256 TSecGroupId )
	{
		ICFSecTSecGroupObj obj = readCachedTSecGroupByIdIdx( TSecGroupId );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeTSecGroupByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		final String S_ProcName = "deepDisposeTSecGroupByTenantIdx";
		ICFSecTSecGroupObj obj;
		List<ICFSecTSecGroupObj> arrayList = readCachedTSecGroupByTenantIdx( TenantId );
		if( arrayList != null )  {
			Iterator<ICFSecTSecGroupObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeTSecGroupByTenantVisIdx( CFLibDbKeyHash256 TenantId,
		boolean IsVisible )
	{
		final String S_ProcName = "deepDisposeTSecGroupByTenantVisIdx";
		ICFSecTSecGroupObj obj;
		List<ICFSecTSecGroupObj> arrayList = readCachedTSecGroupByTenantVisIdx( TenantId,
				IsVisible );
		if( arrayList != null )  {
			Iterator<ICFSecTSecGroupObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeTSecGroupByUNameIdx( CFLibDbKeyHash256 TenantId,
		String Name )
	{
		ICFSecTSecGroupObj obj = readCachedTSecGroupByUNameIdx( TenantId,
				Name );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecTSecGroupObj updateTSecGroup( ICFSecTSecGroupObj Obj ) {
		ICFSecTSecGroupObj obj = Obj;
		schema.getCFSecBackingStore().getTableTSecGroup().updateTSecGroup( null,
			Obj.getTSecGroupRec() );
		obj = (ICFSecTSecGroupObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteTSecGroup( ICFSecTSecGroupObj Obj ) {
		ICFSecTSecGroupObj obj = Obj;
		schema.getCFSecBackingStore().getTableTSecGroup().deleteTSecGroup( null,
			obj.getTSecGroupRec() );
		Obj.forget();
	}

	@Override
	public void deleteTSecGroupByIdIdx( CFLibDbKeyHash256 TSecGroupId )
	{
		ICFSecTSecGroupObj obj = readTSecGroup(TSecGroupId);
		if( obj != null ) {
			ICFSecTSecGroupEditObj editObj = (ICFSecTSecGroupEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFSecTSecGroupEditObj)obj.beginEdit();
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
		deepDisposeTSecGroupByIdIdx( TSecGroupId );
	}

	@Override
	public void deleteTSecGroupByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		ICFSecTSecGroupByTenantIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGroup().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		if( indexByTenantIdx == null ) {
			indexByTenantIdx = new HashMap< ICFSecTSecGroupByTenantIdxKey,
				Map< CFLibDbKeyHash256, ICFSecTSecGroupObj > >();
		}
		if( indexByTenantIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecTSecGroupObj> dict = indexByTenantIdx.get( key );
			schema.getCFSecBackingStore().getTableTSecGroup().deleteTSecGroupByTenantIdx( null,
				TenantId );
			Iterator<ICFSecTSecGroupObj> iter = dict.values().iterator();
			ICFSecTSecGroupObj obj;
			List<ICFSecTSecGroupObj> toForget = new LinkedList<ICFSecTSecGroupObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByTenantIdx.remove( key );
		}
		else {
			schema.getCFSecBackingStore().getTableTSecGroup().deleteTSecGroupByTenantIdx( null,
				TenantId );
		}
		deepDisposeTSecGroupByTenantIdx( TenantId );
	}

	@Override
	public void deleteTSecGroupByTenantVisIdx( CFLibDbKeyHash256 TenantId,
		boolean IsVisible )
	{
		ICFSecTSecGroupByTenantVisIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGroup().newByTenantVisIdxKey();
		key.setRequiredTenantId( TenantId );
		key.setRequiredIsVisible( IsVisible );
		if( indexByTenantVisIdx == null ) {
			indexByTenantVisIdx = new HashMap< ICFSecTSecGroupByTenantVisIdxKey,
				Map< CFLibDbKeyHash256, ICFSecTSecGroupObj > >();
		}
		if( indexByTenantVisIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecTSecGroupObj> dict = indexByTenantVisIdx.get( key );
			schema.getCFSecBackingStore().getTableTSecGroup().deleteTSecGroupByTenantVisIdx( null,
				TenantId,
				IsVisible );
			Iterator<ICFSecTSecGroupObj> iter = dict.values().iterator();
			ICFSecTSecGroupObj obj;
			List<ICFSecTSecGroupObj> toForget = new LinkedList<ICFSecTSecGroupObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByTenantVisIdx.remove( key );
		}
		else {
			schema.getCFSecBackingStore().getTableTSecGroup().deleteTSecGroupByTenantVisIdx( null,
				TenantId,
				IsVisible );
		}
		deepDisposeTSecGroupByTenantVisIdx( TenantId,
				IsVisible );
	}

	@Override
	public void deleteTSecGroupByUNameIdx( CFLibDbKeyHash256 TenantId,
		String Name )
	{
		if( indexByUNameIdx == null ) {
			indexByUNameIdx = new HashMap< ICFSecTSecGroupByUNameIdxKey,
				ICFSecTSecGroupObj >();
		}
		ICFSecTSecGroupByUNameIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGroup().newByUNameIdxKey();
		key.setRequiredTenantId( TenantId );
		key.setRequiredName( Name );
		ICFSecTSecGroupObj obj = null;
		if( indexByUNameIdx.containsKey( key ) ) {
			obj = indexByUNameIdx.get( key );
			schema.getCFSecBackingStore().getTableTSecGroup().deleteTSecGroupByUNameIdx( null,
				TenantId,
				Name );
			obj.forget();
		}
		else {
			schema.getCFSecBackingStore().getTableTSecGroup().deleteTSecGroupByUNameIdx( null,
				TenantId,
				Name );
		}
		deepDisposeTSecGroupByUNameIdx( TenantId,
				Name );
	}
}