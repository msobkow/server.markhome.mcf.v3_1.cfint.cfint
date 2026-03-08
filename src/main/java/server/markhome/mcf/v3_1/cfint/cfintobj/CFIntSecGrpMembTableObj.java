// Description: Java 25 Table Object implementation for SecGrpMemb.

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

public class CFIntSecGrpMembTableObj
	implements ICFIntSecGrpMembTableObj
{
	protected ICFIntSchemaObj schema;
	private Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj> members;
	private Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj> allSecGrpMemb;
	private Map< ICFSecSecGrpMembByClusterIdxKey,
		Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj > > indexByClusterIdx;
	private Map< ICFSecSecGrpMembByGroupIdxKey,
		Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj > > indexByGroupIdx;
	private Map< ICFSecSecGrpMembByUserIdxKey,
		Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj > > indexByUserIdx;
	private Map< ICFSecSecGrpMembByUUserIdxKey,
		ICFSecSecGrpMembObj > indexByUUserIdx;
	public static String TABLE_NAME = "SecGrpMemb";
	public static String TABLE_DBNAME = "secmemb";

	public CFIntSecGrpMembTableObj() {
		schema = null;
		members = new HashMap<CFLibDbKeyHash256, ICFSecSecGrpMembObj>();
		allSecGrpMemb = null;
		indexByClusterIdx = null;
		indexByGroupIdx = null;
		indexByUserIdx = null;
		indexByUUserIdx = null;
	}

	public CFIntSecGrpMembTableObj( ICFSecSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<CFLibDbKeyHash256, ICFSecSecGrpMembObj>();
		allSecGrpMemb = null;
		indexByClusterIdx = null;
		indexByGroupIdx = null;
		indexByUserIdx = null;
		indexByUUserIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFSecSecGrpMembTableObj.getRuntimeClassCode();
	}	

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	public static int getBackingClassCode() {
		return( CFSecSecGrpMembTableObj.getBackingClassCode() );
	}

	/**
	 *	Get the runtime class code for this table; this value is only stable after the application is fully initialized.
	 *
	 *	@return runtimeClassCode
	 */
	public static int getRuntimeClassCode() {
		return( CFSecSecGrpMembTableObj.getRuntimeClassCode() );
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
		allSecGrpMemb = null;
		indexByClusterIdx = null;
		indexByGroupIdx = null;
		indexByUserIdx = null;
		indexByUUserIdx = null;
		List<ICFSecSecGrpMembObj> toForget = new LinkedList<ICFSecSecGrpMembObj>();
		ICFSecSecGrpMembObj cur = null;
		Iterator<ICFSecSecGrpMembObj> iter = members.values().iterator();
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
	 *	CFIntSecGrpMembObj.
	 */
	@Override
	public ICFSecSecGrpMembObj newInstance() {
		ICFSecSecGrpMembObj inst = new CFIntSecGrpMembObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntSecGrpMembObj.
	 */
	@Override
	public ICFSecSecGrpMembEditObj newEditInstance( ICFSecSecGrpMembObj orig ) {
		ICFSecSecGrpMembEditObj edit = new CFIntSecGrpMembEditObj( orig );
		return( edit );
	}

	@Override
	public ICFSecSecGrpMembObj realiseSecGrpMemb( ICFSecSecGrpMembObj Obj ) {
		ICFSecSecGrpMembObj obj = Obj;
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecSecGrpMembObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFSecSecGrpMembObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByClusterIdx != null ) {
				ICFSecSecGrpMembByClusterIdxKey keyClusterIdx =
					schema.getCFSecBackingStore().getFactorySecGrpMemb().newByClusterIdxKey();
				keyClusterIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj > mapClusterIdx = indexByClusterIdx.get( keyClusterIdx );
				if( mapClusterIdx != null ) {
					mapClusterIdx.remove( keepObj.getPKey() );
					if( mapClusterIdx.size() <= 0 ) {
						indexByClusterIdx.remove( keyClusterIdx );
					}
				}
			}

			if( indexByGroupIdx != null ) {
				ICFSecSecGrpMembByGroupIdxKey keyGroupIdx =
					schema.getCFSecBackingStore().getFactorySecGrpMemb().newByGroupIdxKey();
				keyGroupIdx.setRequiredSecGroupId( keepObj.getRequiredSecGroupId() );
				Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj > mapGroupIdx = indexByGroupIdx.get( keyGroupIdx );
				if( mapGroupIdx != null ) {
					mapGroupIdx.remove( keepObj.getPKey() );
					if( mapGroupIdx.size() <= 0 ) {
						indexByGroupIdx.remove( keyGroupIdx );
					}
				}
			}

			if( indexByUserIdx != null ) {
				ICFSecSecGrpMembByUserIdxKey keyUserIdx =
					schema.getCFSecBackingStore().getFactorySecGrpMemb().newByUserIdxKey();
				keyUserIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj > mapUserIdx = indexByUserIdx.get( keyUserIdx );
				if( mapUserIdx != null ) {
					mapUserIdx.remove( keepObj.getPKey() );
					if( mapUserIdx.size() <= 0 ) {
						indexByUserIdx.remove( keyUserIdx );
					}
				}
			}

			if( indexByUUserIdx != null ) {
				ICFSecSecGrpMembByUUserIdxKey keyUUserIdx =
					schema.getCFSecBackingStore().getFactorySecGrpMemb().newByUUserIdxKey();
				keyUUserIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyUUserIdx.setRequiredSecGroupId( keepObj.getRequiredSecGroupId() );
				keyUUserIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				indexByUUserIdx.remove( keyUUserIdx );
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByClusterIdx != null ) {
				ICFSecSecGrpMembByClusterIdxKey keyClusterIdx =
					schema.getCFSecBackingStore().getFactorySecGrpMemb().newByClusterIdxKey();
				keyClusterIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj > mapClusterIdx = indexByClusterIdx.get( keyClusterIdx );
				if( mapClusterIdx != null ) {
					mapClusterIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByGroupIdx != null ) {
				ICFSecSecGrpMembByGroupIdxKey keyGroupIdx =
					schema.getCFSecBackingStore().getFactorySecGrpMemb().newByGroupIdxKey();
				keyGroupIdx.setRequiredSecGroupId( keepObj.getRequiredSecGroupId() );
				Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj > mapGroupIdx = indexByGroupIdx.get( keyGroupIdx );
				if( mapGroupIdx != null ) {
					mapGroupIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUserIdx != null ) {
				ICFSecSecGrpMembByUserIdxKey keyUserIdx =
					schema.getCFSecBackingStore().getFactorySecGrpMemb().newByUserIdxKey();
				keyUserIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj > mapUserIdx = indexByUserIdx.get( keyUserIdx );
				if( mapUserIdx != null ) {
					mapUserIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUUserIdx != null ) {
				ICFSecSecGrpMembByUUserIdxKey keyUUserIdx =
					schema.getCFSecBackingStore().getFactorySecGrpMemb().newByUUserIdxKey();
				keyUUserIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyUUserIdx.setRequiredSecGroupId( keepObj.getRequiredSecGroupId() );
				keyUUserIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				indexByUUserIdx.put( keyUUserIdx, keepObj );
			}

			if( allSecGrpMemb != null ) {
				allSecGrpMemb.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allSecGrpMemb != null ) {
				allSecGrpMemb.put( keepObj.getPKey(), keepObj );
			}

			if( indexByClusterIdx != null ) {
				ICFSecSecGrpMembByClusterIdxKey keyClusterIdx =
					schema.getCFSecBackingStore().getFactorySecGrpMemb().newByClusterIdxKey();
				keyClusterIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj > mapClusterIdx = indexByClusterIdx.get( keyClusterIdx );
				if( mapClusterIdx != null ) {
					mapClusterIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByGroupIdx != null ) {
				ICFSecSecGrpMembByGroupIdxKey keyGroupIdx =
					schema.getCFSecBackingStore().getFactorySecGrpMemb().newByGroupIdxKey();
				keyGroupIdx.setRequiredSecGroupId( keepObj.getRequiredSecGroupId() );
				Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj > mapGroupIdx = indexByGroupIdx.get( keyGroupIdx );
				if( mapGroupIdx != null ) {
					mapGroupIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUserIdx != null ) {
				ICFSecSecGrpMembByUserIdxKey keyUserIdx =
					schema.getCFSecBackingStore().getFactorySecGrpMemb().newByUserIdxKey();
				keyUserIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj > mapUserIdx = indexByUserIdx.get( keyUserIdx );
				if( mapUserIdx != null ) {
					mapUserIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUUserIdx != null ) {
				ICFSecSecGrpMembByUUserIdxKey keyUUserIdx =
					schema.getCFSecBackingStore().getFactorySecGrpMemb().newByUUserIdxKey();
				keyUUserIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyUUserIdx.setRequiredSecGroupId( keepObj.getRequiredSecGroupId() );
				keyUUserIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				indexByUUserIdx.put( keyUUserIdx, keepObj );
			}

		}
		return( keepObj );
	}

	@Override
	public ICFSecSecGrpMembObj createSecGrpMemb( ICFSecSecGrpMembObj Obj ) {
		ICFSecSecGrpMembObj obj = Obj;
		ICFSecSecGrpMemb rec = obj.getSecGrpMembRec();
		schema.getCFSecBackingStore().getTableSecGrpMemb().createSecGrpMemb(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFSecSecGrpMembObj readSecGrpMemb( CFLibDbKeyHash256 pkey ) {
		return( readSecGrpMemb( pkey, false ) );
	}

	@Override
	public ICFSecSecGrpMembObj readSecGrpMemb( CFLibDbKeyHash256 pkey, boolean forceRead ) {
		ICFSecSecGrpMembObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFSecSecGrpMemb readRec = schema.getCFSecBackingStore().getTableSecGrpMemb().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getSecGrpMembTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFSecSecGrpMembObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecSecGrpMembObj readCachedSecGrpMemb( CFLibDbKeyHash256 pkey ) {
		ICFSecSecGrpMembObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeSecGrpMemb( ICFSecSecGrpMembObj obj )
	{
		final String S_ProcName = "CFIntSecGrpMembTableObj.reallyDeepDisposeSecGrpMemb() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecSecGrpMembObj existing = readCachedSecGrpMemb( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFSecSecGrpMembByClusterIdxKey keyClusterIdx = schema.getCFSecBackingStore().getFactorySecGrpMemb().newByClusterIdxKey();
		keyClusterIdx.setRequiredClusterId( existing.getRequiredClusterId() );

		ICFSecSecGrpMembByGroupIdxKey keyGroupIdx = schema.getCFSecBackingStore().getFactorySecGrpMemb().newByGroupIdxKey();
		keyGroupIdx.setRequiredSecGroupId( existing.getRequiredSecGroupId() );

		ICFSecSecGrpMembByUserIdxKey keyUserIdx = schema.getCFSecBackingStore().getFactorySecGrpMemb().newByUserIdxKey();
		keyUserIdx.setRequiredSecUserId( existing.getRequiredSecUserId() );

		ICFSecSecGrpMembByUUserIdxKey keyUUserIdx = schema.getCFSecBackingStore().getFactorySecGrpMemb().newByUUserIdxKey();
		keyUUserIdx.setRequiredClusterId( existing.getRequiredClusterId() );
		keyUUserIdx.setRequiredSecGroupId( existing.getRequiredSecGroupId() );
		keyUUserIdx.setRequiredSecUserId( existing.getRequiredSecUserId() );



		if( indexByClusterIdx != null ) {
			if( indexByClusterIdx.containsKey( keyClusterIdx ) ) {
				indexByClusterIdx.get( keyClusterIdx ).remove( pkey );
				if( indexByClusterIdx.get( keyClusterIdx ).size() <= 0 ) {
					indexByClusterIdx.remove( keyClusterIdx );
				}
			}
		}

		if( indexByGroupIdx != null ) {
			if( indexByGroupIdx.containsKey( keyGroupIdx ) ) {
				indexByGroupIdx.get( keyGroupIdx ).remove( pkey );
				if( indexByGroupIdx.get( keyGroupIdx ).size() <= 0 ) {
					indexByGroupIdx.remove( keyGroupIdx );
				}
			}
		}

		if( indexByUserIdx != null ) {
			if( indexByUserIdx.containsKey( keyUserIdx ) ) {
				indexByUserIdx.get( keyUserIdx ).remove( pkey );
				if( indexByUserIdx.get( keyUserIdx ).size() <= 0 ) {
					indexByUserIdx.remove( keyUserIdx );
				}
			}
		}

		if( indexByUUserIdx != null ) {
			indexByUUserIdx.remove( keyUUserIdx );
		}


	}
	@Override
	public void deepDisposeSecGrpMemb( CFLibDbKeyHash256 pkey ) {
		ICFSecSecGrpMembObj obj = readCachedSecGrpMemb( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecSecGrpMembObj lockSecGrpMemb( CFLibDbKeyHash256 pkey ) {
		ICFSecSecGrpMembObj locked = null;
		ICFSecSecGrpMemb lockRec = schema.getCFSecBackingStore().getTableSecGrpMemb().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getSecGrpMembTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFSecSecGrpMembObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockSecGrpMemb", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFSecSecGrpMembObj> readAllSecGrpMemb() {
		return( readAllSecGrpMemb( false ) );
	}

	@Override
	public List<ICFSecSecGrpMembObj> readAllSecGrpMemb( boolean forceRead ) {
		final String S_ProcName = "readAllSecGrpMemb";
		if( ( allSecGrpMemb == null ) || forceRead ) {
			Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj> map = new HashMap<CFLibDbKeyHash256,ICFSecSecGrpMembObj>();
			allSecGrpMemb = map;
			ICFSecSecGrpMemb[] recList = schema.getCFSecBackingStore().getTableSecGrpMemb().readAllDerived( null );
			ICFSecSecGrpMemb rec;
			ICFSecSecGrpMembObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecGrpMembObj realised = (ICFSecSecGrpMembObj)obj.realise();
			}
		}
		int len = allSecGrpMemb.size();
		ICFSecSecGrpMembObj arr[] = new ICFSecSecGrpMembObj[len];
		Iterator<ICFSecSecGrpMembObj> valIter = allSecGrpMemb.values().iterator();
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
		ArrayList<ICFSecSecGrpMembObj> arrayList = new ArrayList<ICFSecSecGrpMembObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecGrpMembObj> cmp = new Comparator<ICFSecSecGrpMembObj>() {
			@Override
			public int compare( ICFSecSecGrpMembObj lhs, ICFSecSecGrpMembObj rhs ) {
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
		List<ICFSecSecGrpMembObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecSecGrpMembObj> readCachedAllSecGrpMemb() {
		final String S_ProcName = "readCachedAllSecGrpMemb";
		ArrayList<ICFSecSecGrpMembObj> arrayList = new ArrayList<ICFSecSecGrpMembObj>();
		if( allSecGrpMemb != null ) {
			int len = allSecGrpMemb.size();
			ICFSecSecGrpMembObj arr[] = new ICFSecSecGrpMembObj[len];
			Iterator<ICFSecSecGrpMembObj> valIter = allSecGrpMemb.values().iterator();
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
		Comparator<ICFSecSecGrpMembObj> cmp = new Comparator<ICFSecSecGrpMembObj>() {
			public int compare( ICFSecSecGrpMembObj lhs, ICFSecSecGrpMembObj rhs ) {
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
	 *	Return a sorted map of a page of the SecGrpMemb-derived instances in the database.
	 *
	 *	@return	List of ICFSecSecGrpMembObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	@Override
	public List<ICFSecSecGrpMembObj> pageAllSecGrpMemb(CFLibDbKeyHash256 priorSecGrpMembId )
	{
		final String S_ProcName = "pageAllSecGrpMemb";
		Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj> map = new HashMap<CFLibDbKeyHash256,ICFSecSecGrpMembObj>();
		ICFSecSecGrpMemb[] recList = schema.getCFSecBackingStore().getTableSecGrpMemb().pageAllRec( null,
			priorSecGrpMembId );
		ICFSecSecGrpMemb rec;
		ICFSecSecGrpMembObj obj;
		ICFSecSecGrpMembObj realised;
		ArrayList<ICFSecSecGrpMembObj> arrayList = new ArrayList<ICFSecSecGrpMembObj>( recList.length );
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			realised = (ICFSecSecGrpMembObj)obj.realise();
			arrayList.add( realised );
		}
		return( arrayList );
	}

	@Override
	public ICFSecSecGrpMembObj readSecGrpMembByIdIdx( CFLibDbKeyHash256 SecGrpMembId )
	{
		return( readSecGrpMembByIdIdx( SecGrpMembId,
			false ) );
	}

	@Override
	public ICFSecSecGrpMembObj readSecGrpMembByIdIdx( CFLibDbKeyHash256 SecGrpMembId, boolean forceRead )
	{
		ICFSecSecGrpMembObj obj = readSecGrpMemb( SecGrpMembId, forceRead );
		return( obj );
	}

	@Override
	public List<ICFSecSecGrpMembObj> readSecGrpMembByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		return( readSecGrpMembByClusterIdx( ClusterId,
			false ) );
	}

	@Override
	public List<ICFSecSecGrpMembObj> readSecGrpMembByClusterIdx( CFLibDbKeyHash256 ClusterId,
		boolean forceRead )
	{
		final String S_ProcName = "readSecGrpMembByClusterIdx";
		ICFSecSecGrpMembByClusterIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpMemb().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj> dict;
		if( indexByClusterIdx == null ) {
			indexByClusterIdx = new HashMap< ICFSecSecGrpMembByClusterIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecGrpMembObj > >();
		}
		if( ( ! forceRead ) && indexByClusterIdx.containsKey( key ) ) {
			dict = indexByClusterIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecSecGrpMembObj>();
			ICFSecSecGrpMembObj obj;
			ICFSecSecGrpMemb[] recList = schema.getCFSecBackingStore().getTableSecGrpMemb().readDerivedByClusterIdx( null,
				ClusterId );
			ICFSecSecGrpMemb rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getSecGrpMembTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecGrpMembObj realised = (ICFSecSecGrpMembObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByClusterIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecSecGrpMembObj arr[] = new ICFSecSecGrpMembObj[len];
		Iterator<ICFSecSecGrpMembObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecSecGrpMembObj> arrayList = new ArrayList<ICFSecSecGrpMembObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecGrpMembObj> cmp = new Comparator<ICFSecSecGrpMembObj>() {
			@Override
			public int compare( ICFSecSecGrpMembObj lhs, ICFSecSecGrpMembObj rhs ) {
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
		List<ICFSecSecGrpMembObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecSecGrpMembObj> readSecGrpMembByGroupIdx( CFLibDbKeyHash256 SecGroupId )
	{
		return( readSecGrpMembByGroupIdx( SecGroupId,
			false ) );
	}

	@Override
	public List<ICFSecSecGrpMembObj> readSecGrpMembByGroupIdx( CFLibDbKeyHash256 SecGroupId,
		boolean forceRead )
	{
		final String S_ProcName = "readSecGrpMembByGroupIdx";
		ICFSecSecGrpMembByGroupIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpMemb().newByGroupIdxKey();
		key.setRequiredSecGroupId( SecGroupId );
		Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj> dict;
		if( indexByGroupIdx == null ) {
			indexByGroupIdx = new HashMap< ICFSecSecGrpMembByGroupIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecGrpMembObj > >();
		}
		if( ( ! forceRead ) && indexByGroupIdx.containsKey( key ) ) {
			dict = indexByGroupIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecSecGrpMembObj>();
			ICFSecSecGrpMembObj obj;
			ICFSecSecGrpMemb[] recList = schema.getCFSecBackingStore().getTableSecGrpMemb().readDerivedByGroupIdx( null,
				SecGroupId );
			ICFSecSecGrpMemb rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getSecGrpMembTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecGrpMembObj realised = (ICFSecSecGrpMembObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByGroupIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecSecGrpMembObj arr[] = new ICFSecSecGrpMembObj[len];
		Iterator<ICFSecSecGrpMembObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecSecGrpMembObj> arrayList = new ArrayList<ICFSecSecGrpMembObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecGrpMembObj> cmp = new Comparator<ICFSecSecGrpMembObj>() {
			@Override
			public int compare( ICFSecSecGrpMembObj lhs, ICFSecSecGrpMembObj rhs ) {
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
		List<ICFSecSecGrpMembObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecSecGrpMembObj> readSecGrpMembByUserIdx( CFLibDbKeyHash256 SecUserId )
	{
		return( readSecGrpMembByUserIdx( SecUserId,
			false ) );
	}

	@Override
	public List<ICFSecSecGrpMembObj> readSecGrpMembByUserIdx( CFLibDbKeyHash256 SecUserId,
		boolean forceRead )
	{
		final String S_ProcName = "readSecGrpMembByUserIdx";
		ICFSecSecGrpMembByUserIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpMemb().newByUserIdxKey();
		key.setRequiredSecUserId( SecUserId );
		Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj> dict;
		if( indexByUserIdx == null ) {
			indexByUserIdx = new HashMap< ICFSecSecGrpMembByUserIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecGrpMembObj > >();
		}
		if( ( ! forceRead ) && indexByUserIdx.containsKey( key ) ) {
			dict = indexByUserIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecSecGrpMembObj>();
			ICFSecSecGrpMembObj obj;
			ICFSecSecGrpMemb[] recList = schema.getCFSecBackingStore().getTableSecGrpMemb().readDerivedByUserIdx( null,
				SecUserId );
			ICFSecSecGrpMemb rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getSecGrpMembTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecGrpMembObj realised = (ICFSecSecGrpMembObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByUserIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecSecGrpMembObj arr[] = new ICFSecSecGrpMembObj[len];
		Iterator<ICFSecSecGrpMembObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecSecGrpMembObj> arrayList = new ArrayList<ICFSecSecGrpMembObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecGrpMembObj> cmp = new Comparator<ICFSecSecGrpMembObj>() {
			@Override
			public int compare( ICFSecSecGrpMembObj lhs, ICFSecSecGrpMembObj rhs ) {
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
		List<ICFSecSecGrpMembObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFSecSecGrpMembObj readSecGrpMembByUUserIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 SecGroupId,
		CFLibDbKeyHash256 SecUserId )
	{
		return( readSecGrpMembByUUserIdx( ClusterId,
			SecGroupId,
			SecUserId,
			false ) );
	}

	@Override
	public ICFSecSecGrpMembObj readSecGrpMembByUUserIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 SecGroupId,
		CFLibDbKeyHash256 SecUserId, boolean forceRead )
	{
		if( indexByUUserIdx == null ) {
			indexByUUserIdx = new HashMap< ICFSecSecGrpMembByUUserIdxKey,
				ICFSecSecGrpMembObj >();
		}
		ICFSecSecGrpMembByUUserIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpMemb().newByUUserIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredSecGroupId( SecGroupId );
		key.setRequiredSecUserId( SecUserId );
		ICFSecSecGrpMembObj obj = null;
		if( ( ! forceRead ) && indexByUUserIdx.containsKey( key ) ) {
			obj = indexByUUserIdx.get( key );
		}
		else {
			ICFSecSecGrpMemb rec = schema.getCFSecBackingStore().getTableSecGrpMemb().readDerivedByUUserIdx( null,
				ClusterId,
				SecGroupId,
				SecUserId );
			if( rec != null ) {
				obj = schema.getSecGrpMembTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFSecSecGrpMembObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecSecGrpMembObj readCachedSecGrpMembByIdIdx( CFLibDbKeyHash256 SecGrpMembId )
	{
		ICFSecSecGrpMembObj obj = null;
		obj = readCachedSecGrpMemb( SecGrpMembId );
		return( obj );
	}

	@Override
	public List<ICFSecSecGrpMembObj> readCachedSecGrpMembByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		final String S_ProcName = "readCachedSecGrpMembByClusterIdx";
		ICFSecSecGrpMembByClusterIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpMemb().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		ArrayList<ICFSecSecGrpMembObj> arrayList = new ArrayList<ICFSecSecGrpMembObj>();
		if( indexByClusterIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj> dict;
			if( indexByClusterIdx.containsKey( key ) ) {
				dict = indexByClusterIdx.get( key );
				int len = dict.size();
				ICFSecSecGrpMembObj arr[] = new ICFSecSecGrpMembObj[len];
				Iterator<ICFSecSecGrpMembObj> valIter = dict.values().iterator();
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
			ICFSecSecGrpMembObj obj;
			Iterator<ICFSecSecGrpMembObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecSecGrpMembObj> cmp = new Comparator<ICFSecSecGrpMembObj>() {
			@Override
			public int compare( ICFSecSecGrpMembObj lhs, ICFSecSecGrpMembObj rhs ) {
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
	public List<ICFSecSecGrpMembObj> readCachedSecGrpMembByGroupIdx( CFLibDbKeyHash256 SecGroupId )
	{
		final String S_ProcName = "readCachedSecGrpMembByGroupIdx";
		ICFSecSecGrpMembByGroupIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpMemb().newByGroupIdxKey();
		key.setRequiredSecGroupId( SecGroupId );
		ArrayList<ICFSecSecGrpMembObj> arrayList = new ArrayList<ICFSecSecGrpMembObj>();
		if( indexByGroupIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj> dict;
			if( indexByGroupIdx.containsKey( key ) ) {
				dict = indexByGroupIdx.get( key );
				int len = dict.size();
				ICFSecSecGrpMembObj arr[] = new ICFSecSecGrpMembObj[len];
				Iterator<ICFSecSecGrpMembObj> valIter = dict.values().iterator();
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
			ICFSecSecGrpMembObj obj;
			Iterator<ICFSecSecGrpMembObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecSecGrpMembObj> cmp = new Comparator<ICFSecSecGrpMembObj>() {
			@Override
			public int compare( ICFSecSecGrpMembObj lhs, ICFSecSecGrpMembObj rhs ) {
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
	public List<ICFSecSecGrpMembObj> readCachedSecGrpMembByUserIdx( CFLibDbKeyHash256 SecUserId )
	{
		final String S_ProcName = "readCachedSecGrpMembByUserIdx";
		ICFSecSecGrpMembByUserIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpMemb().newByUserIdxKey();
		key.setRequiredSecUserId( SecUserId );
		ArrayList<ICFSecSecGrpMembObj> arrayList = new ArrayList<ICFSecSecGrpMembObj>();
		if( indexByUserIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj> dict;
			if( indexByUserIdx.containsKey( key ) ) {
				dict = indexByUserIdx.get( key );
				int len = dict.size();
				ICFSecSecGrpMembObj arr[] = new ICFSecSecGrpMembObj[len];
				Iterator<ICFSecSecGrpMembObj> valIter = dict.values().iterator();
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
			ICFSecSecGrpMembObj obj;
			Iterator<ICFSecSecGrpMembObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecSecGrpMembObj> cmp = new Comparator<ICFSecSecGrpMembObj>() {
			@Override
			public int compare( ICFSecSecGrpMembObj lhs, ICFSecSecGrpMembObj rhs ) {
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
	public ICFSecSecGrpMembObj readCachedSecGrpMembByUUserIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 SecGroupId,
		CFLibDbKeyHash256 SecUserId )
	{
		ICFSecSecGrpMembObj obj = null;
		ICFSecSecGrpMembByUUserIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpMemb().newByUUserIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredSecGroupId( SecGroupId );
		key.setRequiredSecUserId( SecUserId );
		if( indexByUUserIdx != null ) {
			if( indexByUUserIdx.containsKey( key ) ) {
				obj = indexByUUserIdx.get( key );
			}
			else {
				Iterator<ICFSecSecGrpMembObj> valIter = members.values().iterator();
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
			Iterator<ICFSecSecGrpMembObj> valIter = members.values().iterator();
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
	public void deepDisposeSecGrpMembByIdIdx( CFLibDbKeyHash256 SecGrpMembId )
	{
		ICFSecSecGrpMembObj obj = readCachedSecGrpMembByIdIdx( SecGrpMembId );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeSecGrpMembByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		final String S_ProcName = "deepDisposeSecGrpMembByClusterIdx";
		ICFSecSecGrpMembObj obj;
		List<ICFSecSecGrpMembObj> arrayList = readCachedSecGrpMembByClusterIdx( ClusterId );
		if( arrayList != null )  {
			Iterator<ICFSecSecGrpMembObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeSecGrpMembByGroupIdx( CFLibDbKeyHash256 SecGroupId )
	{
		final String S_ProcName = "deepDisposeSecGrpMembByGroupIdx";
		ICFSecSecGrpMembObj obj;
		List<ICFSecSecGrpMembObj> arrayList = readCachedSecGrpMembByGroupIdx( SecGroupId );
		if( arrayList != null )  {
			Iterator<ICFSecSecGrpMembObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeSecGrpMembByUserIdx( CFLibDbKeyHash256 SecUserId )
	{
		final String S_ProcName = "deepDisposeSecGrpMembByUserIdx";
		ICFSecSecGrpMembObj obj;
		List<ICFSecSecGrpMembObj> arrayList = readCachedSecGrpMembByUserIdx( SecUserId );
		if( arrayList != null )  {
			Iterator<ICFSecSecGrpMembObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeSecGrpMembByUUserIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 SecGroupId,
		CFLibDbKeyHash256 SecUserId )
	{
		ICFSecSecGrpMembObj obj = readCachedSecGrpMembByUUserIdx( ClusterId,
				SecGroupId,
				SecUserId );
		if( obj != null ) {
			obj.forget();
		}
	}

	/**
	 *	Read a page of data as a List of SecGrpMemb-derived instances sorted by their primary keys,
	 *	as identified by the duplicate ClusterIdx key attributes.
	 *
	 *	@param	ClusterId	The SecGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return	A List of SecGrpMemb-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecSecGrpMembObj> pageSecGrpMembByClusterIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 priorSecGrpMembId )
	{
		final String S_ProcName = "pageSecGrpMembByClusterIdx";
		ICFSecSecGrpMembByClusterIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpMemb().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		List<ICFSecSecGrpMembObj> retList = new LinkedList<ICFSecSecGrpMembObj>();
		ICFSecSecGrpMembObj obj;
		ICFSecSecGrpMemb[] recList = schema.getCFSecBackingStore().getTableSecGrpMemb().pageRecByClusterIdx( null,
				ClusterId,
			priorSecGrpMembId );
		ICFSecSecGrpMemb rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getSecGrpMembTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecSecGrpMembObj realised = (ICFSecSecGrpMembObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	/**
	 *	Read a page of data as a List of SecGrpMemb-derived instances sorted by their primary keys,
	 *	as identified by the duplicate GroupIdx key attributes.
	 *
	 *	@param	SecGroupId	The SecGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return	A List of SecGrpMemb-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecSecGrpMembObj> pageSecGrpMembByGroupIdx( CFLibDbKeyHash256 SecGroupId,
		CFLibDbKeyHash256 priorSecGrpMembId )
	{
		final String S_ProcName = "pageSecGrpMembByGroupIdx";
		ICFSecSecGrpMembByGroupIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpMemb().newByGroupIdxKey();
		key.setRequiredSecGroupId( SecGroupId );
		List<ICFSecSecGrpMembObj> retList = new LinkedList<ICFSecSecGrpMembObj>();
		ICFSecSecGrpMembObj obj;
		ICFSecSecGrpMemb[] recList = schema.getCFSecBackingStore().getTableSecGrpMemb().pageRecByGroupIdx( null,
				SecGroupId,
			priorSecGrpMembId );
		ICFSecSecGrpMemb rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getSecGrpMembTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecSecGrpMembObj realised = (ICFSecSecGrpMembObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	/**
	 *	Read a page of data as a List of SecGrpMemb-derived instances sorted by their primary keys,
	 *	as identified by the duplicate UserIdx key attributes.
	 *
	 *	@param	SecUserId	The SecGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return	A List of SecGrpMemb-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecSecGrpMembObj> pageSecGrpMembByUserIdx( CFLibDbKeyHash256 SecUserId,
		CFLibDbKeyHash256 priorSecGrpMembId )
	{
		final String S_ProcName = "pageSecGrpMembByUserIdx";
		ICFSecSecGrpMembByUserIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpMemb().newByUserIdxKey();
		key.setRequiredSecUserId( SecUserId );
		List<ICFSecSecGrpMembObj> retList = new LinkedList<ICFSecSecGrpMembObj>();
		ICFSecSecGrpMembObj obj;
		ICFSecSecGrpMemb[] recList = schema.getCFSecBackingStore().getTableSecGrpMemb().pageRecByUserIdx( null,
				SecUserId,
			priorSecGrpMembId );
		ICFSecSecGrpMemb rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getSecGrpMembTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecSecGrpMembObj realised = (ICFSecSecGrpMembObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	@Override
	public ICFSecSecGrpMembObj updateSecGrpMemb( ICFSecSecGrpMembObj Obj ) {
		ICFSecSecGrpMembObj obj = Obj;
		schema.getCFSecBackingStore().getTableSecGrpMemb().updateSecGrpMemb( null,
			Obj.getSecGrpMembRec() );
		obj = (ICFSecSecGrpMembObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteSecGrpMemb( ICFSecSecGrpMembObj Obj ) {
		ICFSecSecGrpMembObj obj = Obj;
		schema.getCFSecBackingStore().getTableSecGrpMemb().deleteSecGrpMemb( null,
			obj.getSecGrpMembRec() );
		Obj.forget();
	}

	@Override
	public void deleteSecGrpMembByIdIdx( CFLibDbKeyHash256 SecGrpMembId )
	{
		ICFSecSecGrpMembObj obj = readSecGrpMemb(SecGrpMembId);
		if( obj != null ) {
			ICFSecSecGrpMembEditObj editObj = (ICFSecSecGrpMembEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFSecSecGrpMembEditObj)obj.beginEdit();
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
		deepDisposeSecGrpMembByIdIdx( SecGrpMembId );
	}

	@Override
	public void deleteSecGrpMembByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		ICFSecSecGrpMembByClusterIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpMemb().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		if( indexByClusterIdx == null ) {
			indexByClusterIdx = new HashMap< ICFSecSecGrpMembByClusterIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecGrpMembObj > >();
		}
		if( indexByClusterIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj> dict = indexByClusterIdx.get( key );
			schema.getCFSecBackingStore().getTableSecGrpMemb().deleteSecGrpMembByClusterIdx( null,
				ClusterId );
			Iterator<ICFSecSecGrpMembObj> iter = dict.values().iterator();
			ICFSecSecGrpMembObj obj;
			List<ICFSecSecGrpMembObj> toForget = new LinkedList<ICFSecSecGrpMembObj>();
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
			schema.getCFSecBackingStore().getTableSecGrpMemb().deleteSecGrpMembByClusterIdx( null,
				ClusterId );
		}
		deepDisposeSecGrpMembByClusterIdx( ClusterId );
	}

	@Override
	public void deleteSecGrpMembByGroupIdx( CFLibDbKeyHash256 SecGroupId )
	{
		ICFSecSecGrpMembByGroupIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpMemb().newByGroupIdxKey();
		key.setRequiredSecGroupId( SecGroupId );
		if( indexByGroupIdx == null ) {
			indexByGroupIdx = new HashMap< ICFSecSecGrpMembByGroupIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecGrpMembObj > >();
		}
		if( indexByGroupIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj> dict = indexByGroupIdx.get( key );
			schema.getCFSecBackingStore().getTableSecGrpMemb().deleteSecGrpMembByGroupIdx( null,
				SecGroupId );
			Iterator<ICFSecSecGrpMembObj> iter = dict.values().iterator();
			ICFSecSecGrpMembObj obj;
			List<ICFSecSecGrpMembObj> toForget = new LinkedList<ICFSecSecGrpMembObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByGroupIdx.remove( key );
		}
		else {
			schema.getCFSecBackingStore().getTableSecGrpMemb().deleteSecGrpMembByGroupIdx( null,
				SecGroupId );
		}
		deepDisposeSecGrpMembByGroupIdx( SecGroupId );
	}

	@Override
	public void deleteSecGrpMembByUserIdx( CFLibDbKeyHash256 SecUserId )
	{
		ICFSecSecGrpMembByUserIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpMemb().newByUserIdxKey();
		key.setRequiredSecUserId( SecUserId );
		if( indexByUserIdx == null ) {
			indexByUserIdx = new HashMap< ICFSecSecGrpMembByUserIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecGrpMembObj > >();
		}
		if( indexByUserIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecSecGrpMembObj> dict = indexByUserIdx.get( key );
			schema.getCFSecBackingStore().getTableSecGrpMemb().deleteSecGrpMembByUserIdx( null,
				SecUserId );
			Iterator<ICFSecSecGrpMembObj> iter = dict.values().iterator();
			ICFSecSecGrpMembObj obj;
			List<ICFSecSecGrpMembObj> toForget = new LinkedList<ICFSecSecGrpMembObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByUserIdx.remove( key );
		}
		else {
			schema.getCFSecBackingStore().getTableSecGrpMemb().deleteSecGrpMembByUserIdx( null,
				SecUserId );
		}
		deepDisposeSecGrpMembByUserIdx( SecUserId );
	}

	@Override
	public void deleteSecGrpMembByUUserIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 SecGroupId,
		CFLibDbKeyHash256 SecUserId )
	{
		if( indexByUUserIdx == null ) {
			indexByUUserIdx = new HashMap< ICFSecSecGrpMembByUUserIdxKey,
				ICFSecSecGrpMembObj >();
		}
		ICFSecSecGrpMembByUUserIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpMemb().newByUUserIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredSecGroupId( SecGroupId );
		key.setRequiredSecUserId( SecUserId );
		ICFSecSecGrpMembObj obj = null;
		if( indexByUUserIdx.containsKey( key ) ) {
			obj = indexByUUserIdx.get( key );
			schema.getCFSecBackingStore().getTableSecGrpMemb().deleteSecGrpMembByUUserIdx( null,
				ClusterId,
				SecGroupId,
				SecUserId );
			obj.forget();
		}
		else {
			schema.getCFSecBackingStore().getTableSecGrpMemb().deleteSecGrpMembByUUserIdx( null,
				ClusterId,
				SecGroupId,
				SecUserId );
		}
		deepDisposeSecGrpMembByUUserIdx( ClusterId,
				SecGroupId,
				SecUserId );
	}
}