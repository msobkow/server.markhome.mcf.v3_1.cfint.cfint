// Description: Java 25 Table Object implementation for TSecGrpMemb.

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

public class CFIntTSecGrpMembTableObj
	implements ICFIntTSecGrpMembTableObj
{
	protected ICFIntSchemaObj schema;
	private Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj> members;
	private Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj> allTSecGrpMemb;
	private Map< ICFSecTSecGrpMembByTenantIdxKey,
		Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj > > indexByTenantIdx;
	private Map< ICFSecTSecGrpMembByGroupIdxKey,
		Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj > > indexByGroupIdx;
	private Map< ICFSecTSecGrpMembByUserIdxKey,
		Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj > > indexByUserIdx;
	private Map< ICFSecTSecGrpMembByUUserIdxKey,
		ICFSecTSecGrpMembObj > indexByUUserIdx;
	public static String TABLE_NAME = "TSecGrpMemb";
	public static String TABLE_DBNAME = "tsecmemb";

	public CFIntTSecGrpMembTableObj() {
		schema = null;
		members = new HashMap<CFLibDbKeyHash256, ICFSecTSecGrpMembObj>();
		allTSecGrpMemb = null;
		indexByTenantIdx = null;
		indexByGroupIdx = null;
		indexByUserIdx = null;
		indexByUUserIdx = null;
	}

	public CFIntTSecGrpMembTableObj( ICFSecSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<CFLibDbKeyHash256, ICFSecTSecGrpMembObj>();
		allTSecGrpMemb = null;
		indexByTenantIdx = null;
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
		return CFSecTSecGrpMembTableObj.getRuntimeClassCode();
	}	

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	public static int getBackingClassCode() {
		return( CFSecTSecGrpMembTableObj.getBackingClassCode() );
	}

	/**
	 *	Get the runtime class code for this table; this value is only stable after the application is fully initialized.
	 *
	 *	@return runtimeClassCode
	 */
	public static int getRuntimeClassCode() {
		return( CFSecTSecGrpMembTableObj.getRuntimeClassCode() );
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
		allTSecGrpMemb = null;
		indexByTenantIdx = null;
		indexByGroupIdx = null;
		indexByUserIdx = null;
		indexByUUserIdx = null;
		List<ICFSecTSecGrpMembObj> toForget = new LinkedList<ICFSecTSecGrpMembObj>();
		ICFSecTSecGrpMembObj cur = null;
		Iterator<ICFSecTSecGrpMembObj> iter = members.values().iterator();
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
	 *	CFIntTSecGrpMembObj.
	 */
	@Override
	public ICFSecTSecGrpMembObj newInstance() {
		ICFSecTSecGrpMembObj inst = new CFIntTSecGrpMembObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntTSecGrpMembObj.
	 */
	@Override
	public ICFSecTSecGrpMembEditObj newEditInstance( ICFSecTSecGrpMembObj orig ) {
		ICFSecTSecGrpMembEditObj edit = new CFIntTSecGrpMembEditObj( orig );
		return( edit );
	}

	@Override
	public ICFSecTSecGrpMembObj realiseTSecGrpMemb( ICFSecTSecGrpMembObj Obj ) {
		ICFSecTSecGrpMembObj obj = Obj;
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecTSecGrpMembObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFSecTSecGrpMembObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByTenantIdx != null ) {
				ICFSecTSecGrpMembByTenantIdxKey keyTenantIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.remove( keepObj.getPKey() );
					if( mapTenantIdx.size() <= 0 ) {
						indexByTenantIdx.remove( keyTenantIdx );
					}
				}
			}

			if( indexByGroupIdx != null ) {
				ICFSecTSecGrpMembByGroupIdxKey keyGroupIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByGroupIdxKey();
				keyGroupIdx.setRequiredTSecGroupId( keepObj.getRequiredTSecGroupId() );
				Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj > mapGroupIdx = indexByGroupIdx.get( keyGroupIdx );
				if( mapGroupIdx != null ) {
					mapGroupIdx.remove( keepObj.getPKey() );
					if( mapGroupIdx.size() <= 0 ) {
						indexByGroupIdx.remove( keyGroupIdx );
					}
				}
			}

			if( indexByUserIdx != null ) {
				ICFSecTSecGrpMembByUserIdxKey keyUserIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByUserIdxKey();
				keyUserIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj > mapUserIdx = indexByUserIdx.get( keyUserIdx );
				if( mapUserIdx != null ) {
					mapUserIdx.remove( keepObj.getPKey() );
					if( mapUserIdx.size() <= 0 ) {
						indexByUserIdx.remove( keyUserIdx );
					}
				}
			}

			if( indexByUUserIdx != null ) {
				ICFSecTSecGrpMembByUUserIdxKey keyUUserIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByUUserIdxKey();
				keyUUserIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				keyUUserIdx.setRequiredTSecGroupId( keepObj.getRequiredTSecGroupId() );
				keyUUserIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				indexByUUserIdx.remove( keyUUserIdx );
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByTenantIdx != null ) {
				ICFSecTSecGrpMembByTenantIdxKey keyTenantIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByGroupIdx != null ) {
				ICFSecTSecGrpMembByGroupIdxKey keyGroupIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByGroupIdxKey();
				keyGroupIdx.setRequiredTSecGroupId( keepObj.getRequiredTSecGroupId() );
				Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj > mapGroupIdx = indexByGroupIdx.get( keyGroupIdx );
				if( mapGroupIdx != null ) {
					mapGroupIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUserIdx != null ) {
				ICFSecTSecGrpMembByUserIdxKey keyUserIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByUserIdxKey();
				keyUserIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj > mapUserIdx = indexByUserIdx.get( keyUserIdx );
				if( mapUserIdx != null ) {
					mapUserIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUUserIdx != null ) {
				ICFSecTSecGrpMembByUUserIdxKey keyUUserIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByUUserIdxKey();
				keyUUserIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				keyUUserIdx.setRequiredTSecGroupId( keepObj.getRequiredTSecGroupId() );
				keyUUserIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				indexByUUserIdx.put( keyUUserIdx, keepObj );
			}

			if( allTSecGrpMemb != null ) {
				allTSecGrpMemb.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allTSecGrpMemb != null ) {
				allTSecGrpMemb.put( keepObj.getPKey(), keepObj );
			}

			if( indexByTenantIdx != null ) {
				ICFSecTSecGrpMembByTenantIdxKey keyTenantIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByGroupIdx != null ) {
				ICFSecTSecGrpMembByGroupIdxKey keyGroupIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByGroupIdxKey();
				keyGroupIdx.setRequiredTSecGroupId( keepObj.getRequiredTSecGroupId() );
				Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj > mapGroupIdx = indexByGroupIdx.get( keyGroupIdx );
				if( mapGroupIdx != null ) {
					mapGroupIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUserIdx != null ) {
				ICFSecTSecGrpMembByUserIdxKey keyUserIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByUserIdxKey();
				keyUserIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj > mapUserIdx = indexByUserIdx.get( keyUserIdx );
				if( mapUserIdx != null ) {
					mapUserIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUUserIdx != null ) {
				ICFSecTSecGrpMembByUUserIdxKey keyUUserIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByUUserIdxKey();
				keyUUserIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				keyUUserIdx.setRequiredTSecGroupId( keepObj.getRequiredTSecGroupId() );
				keyUUserIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				indexByUUserIdx.put( keyUUserIdx, keepObj );
			}

		}
		return( keepObj );
	}

	@Override
	public ICFSecTSecGrpMembObj createTSecGrpMemb( ICFSecTSecGrpMembObj Obj ) {
		ICFSecTSecGrpMembObj obj = Obj;
		ICFSecTSecGrpMemb rec = obj.getTSecGrpMembRec();
		schema.getCFSecBackingStore().getTableTSecGrpMemb().createTSecGrpMemb(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFSecTSecGrpMembObj readTSecGrpMemb( CFLibDbKeyHash256 pkey ) {
		return( readTSecGrpMemb( pkey, false ) );
	}

	@Override
	public ICFSecTSecGrpMembObj readTSecGrpMemb( CFLibDbKeyHash256 pkey, boolean forceRead ) {
		ICFSecTSecGrpMembObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFSecTSecGrpMemb readRec = schema.getCFSecBackingStore().getTableTSecGrpMemb().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getTSecGrpMembTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFSecTSecGrpMembObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecTSecGrpMembObj readCachedTSecGrpMemb( CFLibDbKeyHash256 pkey ) {
		ICFSecTSecGrpMembObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeTSecGrpMemb( ICFSecTSecGrpMembObj obj )
	{
		final String S_ProcName = "CFIntTSecGrpMembTableObj.reallyDeepDisposeTSecGrpMemb() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecTSecGrpMembObj existing = readCachedTSecGrpMemb( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFSecTSecGrpMembByTenantIdxKey keyTenantIdx = schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByTenantIdxKey();
		keyTenantIdx.setRequiredTenantId( existing.getRequiredTenantId() );

		ICFSecTSecGrpMembByGroupIdxKey keyGroupIdx = schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByGroupIdxKey();
		keyGroupIdx.setRequiredTSecGroupId( existing.getRequiredTSecGroupId() );

		ICFSecTSecGrpMembByUserIdxKey keyUserIdx = schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByUserIdxKey();
		keyUserIdx.setRequiredSecUserId( existing.getRequiredSecUserId() );

		ICFSecTSecGrpMembByUUserIdxKey keyUUserIdx = schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByUUserIdxKey();
		keyUUserIdx.setRequiredTenantId( existing.getRequiredTenantId() );
		keyUUserIdx.setRequiredTSecGroupId( existing.getRequiredTSecGroupId() );
		keyUUserIdx.setRequiredSecUserId( existing.getRequiredSecUserId() );



		if( indexByTenantIdx != null ) {
			if( indexByTenantIdx.containsKey( keyTenantIdx ) ) {
				indexByTenantIdx.get( keyTenantIdx ).remove( pkey );
				if( indexByTenantIdx.get( keyTenantIdx ).size() <= 0 ) {
					indexByTenantIdx.remove( keyTenantIdx );
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
	public void deepDisposeTSecGrpMemb( CFLibDbKeyHash256 pkey ) {
		ICFSecTSecGrpMembObj obj = readCachedTSecGrpMemb( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecTSecGrpMembObj lockTSecGrpMemb( CFLibDbKeyHash256 pkey ) {
		ICFSecTSecGrpMembObj locked = null;
		ICFSecTSecGrpMemb lockRec = schema.getCFSecBackingStore().getTableTSecGrpMemb().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getTSecGrpMembTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFSecTSecGrpMembObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockTSecGrpMemb", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFSecTSecGrpMembObj> readAllTSecGrpMemb() {
		return( readAllTSecGrpMemb( false ) );
	}

	@Override
	public List<ICFSecTSecGrpMembObj> readAllTSecGrpMemb( boolean forceRead ) {
		final String S_ProcName = "readAllTSecGrpMemb";
		if( ( allTSecGrpMemb == null ) || forceRead ) {
			Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj> map = new HashMap<CFLibDbKeyHash256,ICFSecTSecGrpMembObj>();
			allTSecGrpMemb = map;
			ICFSecTSecGrpMemb[] recList = schema.getCFSecBackingStore().getTableTSecGrpMemb().readAllDerived( null );
			ICFSecTSecGrpMemb rec;
			ICFSecTSecGrpMembObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecTSecGrpMembObj realised = (ICFSecTSecGrpMembObj)obj.realise();
			}
		}
		int len = allTSecGrpMemb.size();
		ICFSecTSecGrpMembObj arr[] = new ICFSecTSecGrpMembObj[len];
		Iterator<ICFSecTSecGrpMembObj> valIter = allTSecGrpMemb.values().iterator();
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
		ArrayList<ICFSecTSecGrpMembObj> arrayList = new ArrayList<ICFSecTSecGrpMembObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecTSecGrpMembObj> cmp = new Comparator<ICFSecTSecGrpMembObj>() {
			@Override
			public int compare( ICFSecTSecGrpMembObj lhs, ICFSecTSecGrpMembObj rhs ) {
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
		List<ICFSecTSecGrpMembObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecTSecGrpMembObj> readCachedAllTSecGrpMemb() {
		final String S_ProcName = "readCachedAllTSecGrpMemb";
		ArrayList<ICFSecTSecGrpMembObj> arrayList = new ArrayList<ICFSecTSecGrpMembObj>();
		if( allTSecGrpMemb != null ) {
			int len = allTSecGrpMemb.size();
			ICFSecTSecGrpMembObj arr[] = new ICFSecTSecGrpMembObj[len];
			Iterator<ICFSecTSecGrpMembObj> valIter = allTSecGrpMemb.values().iterator();
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
		Comparator<ICFSecTSecGrpMembObj> cmp = new Comparator<ICFSecTSecGrpMembObj>() {
			public int compare( ICFSecTSecGrpMembObj lhs, ICFSecTSecGrpMembObj rhs ) {
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
	 *	Return a sorted map of a page of the TSecGrpMemb-derived instances in the database.
	 *
	 *	@return	List of ICFSecTSecGrpMembObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	@Override
	public List<ICFSecTSecGrpMembObj> pageAllTSecGrpMemb(CFLibDbKeyHash256 priorTSecGrpMembId )
	{
		final String S_ProcName = "pageAllTSecGrpMemb";
		Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj> map = new HashMap<CFLibDbKeyHash256,ICFSecTSecGrpMembObj>();
		ICFSecTSecGrpMemb[] recList = schema.getCFSecBackingStore().getTableTSecGrpMemb().pageAllRec( null,
			priorTSecGrpMembId );
		ICFSecTSecGrpMemb rec;
		ICFSecTSecGrpMembObj obj;
		ICFSecTSecGrpMembObj realised;
		ArrayList<ICFSecTSecGrpMembObj> arrayList = new ArrayList<ICFSecTSecGrpMembObj>( recList.length );
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			realised = (ICFSecTSecGrpMembObj)obj.realise();
			arrayList.add( realised );
		}
		return( arrayList );
	}

	@Override
	public ICFSecTSecGrpMembObj readTSecGrpMembByIdIdx( CFLibDbKeyHash256 TSecGrpMembId )
	{
		return( readTSecGrpMembByIdIdx( TSecGrpMembId,
			false ) );
	}

	@Override
	public ICFSecTSecGrpMembObj readTSecGrpMembByIdIdx( CFLibDbKeyHash256 TSecGrpMembId, boolean forceRead )
	{
		ICFSecTSecGrpMembObj obj = readTSecGrpMemb( TSecGrpMembId, forceRead );
		return( obj );
	}

	@Override
	public List<ICFSecTSecGrpMembObj> readTSecGrpMembByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		return( readTSecGrpMembByTenantIdx( TenantId,
			false ) );
	}

	@Override
	public List<ICFSecTSecGrpMembObj> readTSecGrpMembByTenantIdx( CFLibDbKeyHash256 TenantId,
		boolean forceRead )
	{
		final String S_ProcName = "readTSecGrpMembByTenantIdx";
		ICFSecTSecGrpMembByTenantIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj> dict;
		if( indexByTenantIdx == null ) {
			indexByTenantIdx = new HashMap< ICFSecTSecGrpMembByTenantIdxKey,
				Map< CFLibDbKeyHash256, ICFSecTSecGrpMembObj > >();
		}
		if( ( ! forceRead ) && indexByTenantIdx.containsKey( key ) ) {
			dict = indexByTenantIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecTSecGrpMembObj>();
			ICFSecTSecGrpMembObj obj;
			ICFSecTSecGrpMemb[] recList = schema.getCFSecBackingStore().getTableTSecGrpMemb().readDerivedByTenantIdx( null,
				TenantId );
			ICFSecTSecGrpMemb rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getTSecGrpMembTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecTSecGrpMembObj realised = (ICFSecTSecGrpMembObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByTenantIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecTSecGrpMembObj arr[] = new ICFSecTSecGrpMembObj[len];
		Iterator<ICFSecTSecGrpMembObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecTSecGrpMembObj> arrayList = new ArrayList<ICFSecTSecGrpMembObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecTSecGrpMembObj> cmp = new Comparator<ICFSecTSecGrpMembObj>() {
			@Override
			public int compare( ICFSecTSecGrpMembObj lhs, ICFSecTSecGrpMembObj rhs ) {
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
		List<ICFSecTSecGrpMembObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecTSecGrpMembObj> readTSecGrpMembByGroupIdx( CFLibDbKeyHash256 TSecGroupId )
	{
		return( readTSecGrpMembByGroupIdx( TSecGroupId,
			false ) );
	}

	@Override
	public List<ICFSecTSecGrpMembObj> readTSecGrpMembByGroupIdx( CFLibDbKeyHash256 TSecGroupId,
		boolean forceRead )
	{
		final String S_ProcName = "readTSecGrpMembByGroupIdx";
		ICFSecTSecGrpMembByGroupIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByGroupIdxKey();
		key.setRequiredTSecGroupId( TSecGroupId );
		Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj> dict;
		if( indexByGroupIdx == null ) {
			indexByGroupIdx = new HashMap< ICFSecTSecGrpMembByGroupIdxKey,
				Map< CFLibDbKeyHash256, ICFSecTSecGrpMembObj > >();
		}
		if( ( ! forceRead ) && indexByGroupIdx.containsKey( key ) ) {
			dict = indexByGroupIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecTSecGrpMembObj>();
			ICFSecTSecGrpMembObj obj;
			ICFSecTSecGrpMemb[] recList = schema.getCFSecBackingStore().getTableTSecGrpMemb().readDerivedByGroupIdx( null,
				TSecGroupId );
			ICFSecTSecGrpMemb rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getTSecGrpMembTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecTSecGrpMembObj realised = (ICFSecTSecGrpMembObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByGroupIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecTSecGrpMembObj arr[] = new ICFSecTSecGrpMembObj[len];
		Iterator<ICFSecTSecGrpMembObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecTSecGrpMembObj> arrayList = new ArrayList<ICFSecTSecGrpMembObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecTSecGrpMembObj> cmp = new Comparator<ICFSecTSecGrpMembObj>() {
			@Override
			public int compare( ICFSecTSecGrpMembObj lhs, ICFSecTSecGrpMembObj rhs ) {
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
		List<ICFSecTSecGrpMembObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecTSecGrpMembObj> readTSecGrpMembByUserIdx( CFLibDbKeyHash256 SecUserId )
	{
		return( readTSecGrpMembByUserIdx( SecUserId,
			false ) );
	}

	@Override
	public List<ICFSecTSecGrpMembObj> readTSecGrpMembByUserIdx( CFLibDbKeyHash256 SecUserId,
		boolean forceRead )
	{
		final String S_ProcName = "readTSecGrpMembByUserIdx";
		ICFSecTSecGrpMembByUserIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByUserIdxKey();
		key.setRequiredSecUserId( SecUserId );
		Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj> dict;
		if( indexByUserIdx == null ) {
			indexByUserIdx = new HashMap< ICFSecTSecGrpMembByUserIdxKey,
				Map< CFLibDbKeyHash256, ICFSecTSecGrpMembObj > >();
		}
		if( ( ! forceRead ) && indexByUserIdx.containsKey( key ) ) {
			dict = indexByUserIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecTSecGrpMembObj>();
			ICFSecTSecGrpMembObj obj;
			ICFSecTSecGrpMemb[] recList = schema.getCFSecBackingStore().getTableTSecGrpMemb().readDerivedByUserIdx( null,
				SecUserId );
			ICFSecTSecGrpMemb rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getTSecGrpMembTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecTSecGrpMembObj realised = (ICFSecTSecGrpMembObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByUserIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecTSecGrpMembObj arr[] = new ICFSecTSecGrpMembObj[len];
		Iterator<ICFSecTSecGrpMembObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecTSecGrpMembObj> arrayList = new ArrayList<ICFSecTSecGrpMembObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecTSecGrpMembObj> cmp = new Comparator<ICFSecTSecGrpMembObj>() {
			@Override
			public int compare( ICFSecTSecGrpMembObj lhs, ICFSecTSecGrpMembObj rhs ) {
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
		List<ICFSecTSecGrpMembObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFSecTSecGrpMembObj readTSecGrpMembByUUserIdx( CFLibDbKeyHash256 TenantId,
		CFLibDbKeyHash256 TSecGroupId,
		CFLibDbKeyHash256 SecUserId )
	{
		return( readTSecGrpMembByUUserIdx( TenantId,
			TSecGroupId,
			SecUserId,
			false ) );
	}

	@Override
	public ICFSecTSecGrpMembObj readTSecGrpMembByUUserIdx( CFLibDbKeyHash256 TenantId,
		CFLibDbKeyHash256 TSecGroupId,
		CFLibDbKeyHash256 SecUserId, boolean forceRead )
	{
		if( indexByUUserIdx == null ) {
			indexByUUserIdx = new HashMap< ICFSecTSecGrpMembByUUserIdxKey,
				ICFSecTSecGrpMembObj >();
		}
		ICFSecTSecGrpMembByUUserIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByUUserIdxKey();
		key.setRequiredTenantId( TenantId );
		key.setRequiredTSecGroupId( TSecGroupId );
		key.setRequiredSecUserId( SecUserId );
		ICFSecTSecGrpMembObj obj = null;
		if( ( ! forceRead ) && indexByUUserIdx.containsKey( key ) ) {
			obj = indexByUUserIdx.get( key );
		}
		else {
			ICFSecTSecGrpMemb rec = schema.getCFSecBackingStore().getTableTSecGrpMemb().readDerivedByUUserIdx( null,
				TenantId,
				TSecGroupId,
				SecUserId );
			if( rec != null ) {
				obj = schema.getTSecGrpMembTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFSecTSecGrpMembObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecTSecGrpMembObj readCachedTSecGrpMembByIdIdx( CFLibDbKeyHash256 TSecGrpMembId )
	{
		ICFSecTSecGrpMembObj obj = null;
		obj = readCachedTSecGrpMemb( TSecGrpMembId );
		return( obj );
	}

	@Override
	public List<ICFSecTSecGrpMembObj> readCachedTSecGrpMembByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		final String S_ProcName = "readCachedTSecGrpMembByTenantIdx";
		ICFSecTSecGrpMembByTenantIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		ArrayList<ICFSecTSecGrpMembObj> arrayList = new ArrayList<ICFSecTSecGrpMembObj>();
		if( indexByTenantIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj> dict;
			if( indexByTenantIdx.containsKey( key ) ) {
				dict = indexByTenantIdx.get( key );
				int len = dict.size();
				ICFSecTSecGrpMembObj arr[] = new ICFSecTSecGrpMembObj[len];
				Iterator<ICFSecTSecGrpMembObj> valIter = dict.values().iterator();
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
			ICFSecTSecGrpMembObj obj;
			Iterator<ICFSecTSecGrpMembObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecTSecGrpMembObj> cmp = new Comparator<ICFSecTSecGrpMembObj>() {
			@Override
			public int compare( ICFSecTSecGrpMembObj lhs, ICFSecTSecGrpMembObj rhs ) {
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
	public List<ICFSecTSecGrpMembObj> readCachedTSecGrpMembByGroupIdx( CFLibDbKeyHash256 TSecGroupId )
	{
		final String S_ProcName = "readCachedTSecGrpMembByGroupIdx";
		ICFSecTSecGrpMembByGroupIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByGroupIdxKey();
		key.setRequiredTSecGroupId( TSecGroupId );
		ArrayList<ICFSecTSecGrpMembObj> arrayList = new ArrayList<ICFSecTSecGrpMembObj>();
		if( indexByGroupIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj> dict;
			if( indexByGroupIdx.containsKey( key ) ) {
				dict = indexByGroupIdx.get( key );
				int len = dict.size();
				ICFSecTSecGrpMembObj arr[] = new ICFSecTSecGrpMembObj[len];
				Iterator<ICFSecTSecGrpMembObj> valIter = dict.values().iterator();
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
			ICFSecTSecGrpMembObj obj;
			Iterator<ICFSecTSecGrpMembObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecTSecGrpMembObj> cmp = new Comparator<ICFSecTSecGrpMembObj>() {
			@Override
			public int compare( ICFSecTSecGrpMembObj lhs, ICFSecTSecGrpMembObj rhs ) {
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
	public List<ICFSecTSecGrpMembObj> readCachedTSecGrpMembByUserIdx( CFLibDbKeyHash256 SecUserId )
	{
		final String S_ProcName = "readCachedTSecGrpMembByUserIdx";
		ICFSecTSecGrpMembByUserIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByUserIdxKey();
		key.setRequiredSecUserId( SecUserId );
		ArrayList<ICFSecTSecGrpMembObj> arrayList = new ArrayList<ICFSecTSecGrpMembObj>();
		if( indexByUserIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj> dict;
			if( indexByUserIdx.containsKey( key ) ) {
				dict = indexByUserIdx.get( key );
				int len = dict.size();
				ICFSecTSecGrpMembObj arr[] = new ICFSecTSecGrpMembObj[len];
				Iterator<ICFSecTSecGrpMembObj> valIter = dict.values().iterator();
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
			ICFSecTSecGrpMembObj obj;
			Iterator<ICFSecTSecGrpMembObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecTSecGrpMembObj> cmp = new Comparator<ICFSecTSecGrpMembObj>() {
			@Override
			public int compare( ICFSecTSecGrpMembObj lhs, ICFSecTSecGrpMembObj rhs ) {
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
	public ICFSecTSecGrpMembObj readCachedTSecGrpMembByUUserIdx( CFLibDbKeyHash256 TenantId,
		CFLibDbKeyHash256 TSecGroupId,
		CFLibDbKeyHash256 SecUserId )
	{
		ICFSecTSecGrpMembObj obj = null;
		ICFSecTSecGrpMembByUUserIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByUUserIdxKey();
		key.setRequiredTenantId( TenantId );
		key.setRequiredTSecGroupId( TSecGroupId );
		key.setRequiredSecUserId( SecUserId );
		if( indexByUUserIdx != null ) {
			if( indexByUUserIdx.containsKey( key ) ) {
				obj = indexByUUserIdx.get( key );
			}
			else {
				Iterator<ICFSecTSecGrpMembObj> valIter = members.values().iterator();
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
			Iterator<ICFSecTSecGrpMembObj> valIter = members.values().iterator();
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
	public void deepDisposeTSecGrpMembByIdIdx( CFLibDbKeyHash256 TSecGrpMembId )
	{
		ICFSecTSecGrpMembObj obj = readCachedTSecGrpMembByIdIdx( TSecGrpMembId );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeTSecGrpMembByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		final String S_ProcName = "deepDisposeTSecGrpMembByTenantIdx";
		ICFSecTSecGrpMembObj obj;
		List<ICFSecTSecGrpMembObj> arrayList = readCachedTSecGrpMembByTenantIdx( TenantId );
		if( arrayList != null )  {
			Iterator<ICFSecTSecGrpMembObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeTSecGrpMembByGroupIdx( CFLibDbKeyHash256 TSecGroupId )
	{
		final String S_ProcName = "deepDisposeTSecGrpMembByGroupIdx";
		ICFSecTSecGrpMembObj obj;
		List<ICFSecTSecGrpMembObj> arrayList = readCachedTSecGrpMembByGroupIdx( TSecGroupId );
		if( arrayList != null )  {
			Iterator<ICFSecTSecGrpMembObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeTSecGrpMembByUserIdx( CFLibDbKeyHash256 SecUserId )
	{
		final String S_ProcName = "deepDisposeTSecGrpMembByUserIdx";
		ICFSecTSecGrpMembObj obj;
		List<ICFSecTSecGrpMembObj> arrayList = readCachedTSecGrpMembByUserIdx( SecUserId );
		if( arrayList != null )  {
			Iterator<ICFSecTSecGrpMembObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeTSecGrpMembByUUserIdx( CFLibDbKeyHash256 TenantId,
		CFLibDbKeyHash256 TSecGroupId,
		CFLibDbKeyHash256 SecUserId )
	{
		ICFSecTSecGrpMembObj obj = readCachedTSecGrpMembByUUserIdx( TenantId,
				TSecGroupId,
				SecUserId );
		if( obj != null ) {
			obj.forget();
		}
	}

	/**
	 *	Read a page of data as a List of TSecGrpMemb-derived instances sorted by their primary keys,
	 *	as identified by the duplicate TenantIdx key attributes.
	 *
	 *	@param	TenantId	The TSecGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return	A List of TSecGrpMemb-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecTSecGrpMembObj> pageTSecGrpMembByTenantIdx( CFLibDbKeyHash256 TenantId,
		CFLibDbKeyHash256 priorTSecGrpMembId )
	{
		final String S_ProcName = "pageTSecGrpMembByTenantIdx";
		ICFSecTSecGrpMembByTenantIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		List<ICFSecTSecGrpMembObj> retList = new LinkedList<ICFSecTSecGrpMembObj>();
		ICFSecTSecGrpMembObj obj;
		ICFSecTSecGrpMemb[] recList = schema.getCFSecBackingStore().getTableTSecGrpMemb().pageRecByTenantIdx( null,
				TenantId,
			priorTSecGrpMembId );
		ICFSecTSecGrpMemb rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getTSecGrpMembTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecTSecGrpMembObj realised = (ICFSecTSecGrpMembObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	/**
	 *	Read a page of data as a List of TSecGrpMemb-derived instances sorted by their primary keys,
	 *	as identified by the duplicate GroupIdx key attributes.
	 *
	 *	@param	TSecGroupId	The TSecGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return	A List of TSecGrpMemb-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecTSecGrpMembObj> pageTSecGrpMembByGroupIdx( CFLibDbKeyHash256 TSecGroupId,
		CFLibDbKeyHash256 priorTSecGrpMembId )
	{
		final String S_ProcName = "pageTSecGrpMembByGroupIdx";
		ICFSecTSecGrpMembByGroupIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByGroupIdxKey();
		key.setRequiredTSecGroupId( TSecGroupId );
		List<ICFSecTSecGrpMembObj> retList = new LinkedList<ICFSecTSecGrpMembObj>();
		ICFSecTSecGrpMembObj obj;
		ICFSecTSecGrpMemb[] recList = schema.getCFSecBackingStore().getTableTSecGrpMemb().pageRecByGroupIdx( null,
				TSecGroupId,
			priorTSecGrpMembId );
		ICFSecTSecGrpMemb rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getTSecGrpMembTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecTSecGrpMembObj realised = (ICFSecTSecGrpMembObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	/**
	 *	Read a page of data as a List of TSecGrpMemb-derived instances sorted by their primary keys,
	 *	as identified by the duplicate UserIdx key attributes.
	 *
	 *	@param	SecUserId	The TSecGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return	A List of TSecGrpMemb-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecTSecGrpMembObj> pageTSecGrpMembByUserIdx( CFLibDbKeyHash256 SecUserId,
		CFLibDbKeyHash256 priorTSecGrpMembId )
	{
		final String S_ProcName = "pageTSecGrpMembByUserIdx";
		ICFSecTSecGrpMembByUserIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByUserIdxKey();
		key.setRequiredSecUserId( SecUserId );
		List<ICFSecTSecGrpMembObj> retList = new LinkedList<ICFSecTSecGrpMembObj>();
		ICFSecTSecGrpMembObj obj;
		ICFSecTSecGrpMemb[] recList = schema.getCFSecBackingStore().getTableTSecGrpMemb().pageRecByUserIdx( null,
				SecUserId,
			priorTSecGrpMembId );
		ICFSecTSecGrpMemb rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getTSecGrpMembTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecTSecGrpMembObj realised = (ICFSecTSecGrpMembObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	@Override
	public ICFSecTSecGrpMembObj updateTSecGrpMemb( ICFSecTSecGrpMembObj Obj ) {
		ICFSecTSecGrpMembObj obj = Obj;
		schema.getCFSecBackingStore().getTableTSecGrpMemb().updateTSecGrpMemb( null,
			Obj.getTSecGrpMembRec() );
		obj = (ICFSecTSecGrpMembObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteTSecGrpMemb( ICFSecTSecGrpMembObj Obj ) {
		ICFSecTSecGrpMembObj obj = Obj;
		schema.getCFSecBackingStore().getTableTSecGrpMemb().deleteTSecGrpMemb( null,
			obj.getTSecGrpMembRec() );
		Obj.forget();
	}

	@Override
	public void deleteTSecGrpMembByIdIdx( CFLibDbKeyHash256 TSecGrpMembId )
	{
		ICFSecTSecGrpMembObj obj = readTSecGrpMemb(TSecGrpMembId);
		if( obj != null ) {
			ICFSecTSecGrpMembEditObj editObj = (ICFSecTSecGrpMembEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFSecTSecGrpMembEditObj)obj.beginEdit();
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
		deepDisposeTSecGrpMembByIdIdx( TSecGrpMembId );
	}

	@Override
	public void deleteTSecGrpMembByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		ICFSecTSecGrpMembByTenantIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		if( indexByTenantIdx == null ) {
			indexByTenantIdx = new HashMap< ICFSecTSecGrpMembByTenantIdxKey,
				Map< CFLibDbKeyHash256, ICFSecTSecGrpMembObj > >();
		}
		if( indexByTenantIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj> dict = indexByTenantIdx.get( key );
			schema.getCFSecBackingStore().getTableTSecGrpMemb().deleteTSecGrpMembByTenantIdx( null,
				TenantId );
			Iterator<ICFSecTSecGrpMembObj> iter = dict.values().iterator();
			ICFSecTSecGrpMembObj obj;
			List<ICFSecTSecGrpMembObj> toForget = new LinkedList<ICFSecTSecGrpMembObj>();
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
			schema.getCFSecBackingStore().getTableTSecGrpMemb().deleteTSecGrpMembByTenantIdx( null,
				TenantId );
		}
		deepDisposeTSecGrpMembByTenantIdx( TenantId );
	}

	@Override
	public void deleteTSecGrpMembByGroupIdx( CFLibDbKeyHash256 TSecGroupId )
	{
		ICFSecTSecGrpMembByGroupIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByGroupIdxKey();
		key.setRequiredTSecGroupId( TSecGroupId );
		if( indexByGroupIdx == null ) {
			indexByGroupIdx = new HashMap< ICFSecTSecGrpMembByGroupIdxKey,
				Map< CFLibDbKeyHash256, ICFSecTSecGrpMembObj > >();
		}
		if( indexByGroupIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj> dict = indexByGroupIdx.get( key );
			schema.getCFSecBackingStore().getTableTSecGrpMemb().deleteTSecGrpMembByGroupIdx( null,
				TSecGroupId );
			Iterator<ICFSecTSecGrpMembObj> iter = dict.values().iterator();
			ICFSecTSecGrpMembObj obj;
			List<ICFSecTSecGrpMembObj> toForget = new LinkedList<ICFSecTSecGrpMembObj>();
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
			schema.getCFSecBackingStore().getTableTSecGrpMemb().deleteTSecGrpMembByGroupIdx( null,
				TSecGroupId );
		}
		deepDisposeTSecGrpMembByGroupIdx( TSecGroupId );
	}

	@Override
	public void deleteTSecGrpMembByUserIdx( CFLibDbKeyHash256 SecUserId )
	{
		ICFSecTSecGrpMembByUserIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByUserIdxKey();
		key.setRequiredSecUserId( SecUserId );
		if( indexByUserIdx == null ) {
			indexByUserIdx = new HashMap< ICFSecTSecGrpMembByUserIdxKey,
				Map< CFLibDbKeyHash256, ICFSecTSecGrpMembObj > >();
		}
		if( indexByUserIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecTSecGrpMembObj> dict = indexByUserIdx.get( key );
			schema.getCFSecBackingStore().getTableTSecGrpMemb().deleteTSecGrpMembByUserIdx( null,
				SecUserId );
			Iterator<ICFSecTSecGrpMembObj> iter = dict.values().iterator();
			ICFSecTSecGrpMembObj obj;
			List<ICFSecTSecGrpMembObj> toForget = new LinkedList<ICFSecTSecGrpMembObj>();
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
			schema.getCFSecBackingStore().getTableTSecGrpMemb().deleteTSecGrpMembByUserIdx( null,
				SecUserId );
		}
		deepDisposeTSecGrpMembByUserIdx( SecUserId );
	}

	@Override
	public void deleteTSecGrpMembByUUserIdx( CFLibDbKeyHash256 TenantId,
		CFLibDbKeyHash256 TSecGroupId,
		CFLibDbKeyHash256 SecUserId )
	{
		if( indexByUUserIdx == null ) {
			indexByUUserIdx = new HashMap< ICFSecTSecGrpMembByUUserIdxKey,
				ICFSecTSecGrpMembObj >();
		}
		ICFSecTSecGrpMembByUUserIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpMemb().newByUUserIdxKey();
		key.setRequiredTenantId( TenantId );
		key.setRequiredTSecGroupId( TSecGroupId );
		key.setRequiredSecUserId( SecUserId );
		ICFSecTSecGrpMembObj obj = null;
		if( indexByUUserIdx.containsKey( key ) ) {
			obj = indexByUUserIdx.get( key );
			schema.getCFSecBackingStore().getTableTSecGrpMemb().deleteTSecGrpMembByUUserIdx( null,
				TenantId,
				TSecGroupId,
				SecUserId );
			obj.forget();
		}
		else {
			schema.getCFSecBackingStore().getTableTSecGrpMemb().deleteTSecGrpMembByUUserIdx( null,
				TenantId,
				TSecGroupId,
				SecUserId );
		}
		deepDisposeTSecGrpMembByUUserIdx( TenantId,
				TSecGroupId,
				SecUserId );
	}
}