// Description: Java 25 Table Object implementation for SecGrpInc.

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

public class CFIntSecGrpIncTableObj
	implements ICFIntSecGrpIncTableObj
{
	protected ICFIntSchemaObj schema;
	private Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj> members;
	private Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj> allSecGrpInc;
	private Map< ICFSecSecGrpIncByClusterIdxKey,
		Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj > > indexByClusterIdx;
	private Map< ICFSecSecGrpIncByGroupIdxKey,
		Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj > > indexByGroupIdx;
	private Map< ICFSecSecGrpIncByIncludeIdxKey,
		Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj > > indexByIncludeIdx;
	private Map< ICFSecSecGrpIncByUIncludeIdxKey,
		ICFSecSecGrpIncObj > indexByUIncludeIdx;
	public static String TABLE_NAME = "SecGrpInc";
	public static String TABLE_DBNAME = "secinc";

	public CFIntSecGrpIncTableObj() {
		schema = null;
		members = new HashMap<CFLibDbKeyHash256, ICFSecSecGrpIncObj>();
		allSecGrpInc = null;
		indexByClusterIdx = null;
		indexByGroupIdx = null;
		indexByIncludeIdx = null;
		indexByUIncludeIdx = null;
	}

	public CFIntSecGrpIncTableObj( ICFSecSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<CFLibDbKeyHash256, ICFSecSecGrpIncObj>();
		allSecGrpInc = null;
		indexByClusterIdx = null;
		indexByGroupIdx = null;
		indexByIncludeIdx = null;
		indexByUIncludeIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFSecSecGrpIncTableObj.getRuntimeClassCode();
	}	

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	public static int getBackingClassCode() {
		return( CFSecSecGrpIncTableObj.getBackingClassCode() );
	}

	/**
	 *	Get the runtime class code for this table; this value is only stable after the application is fully initialized.
	 *
	 *	@return runtimeClassCode
	 */
	public static int getRuntimeClassCode() {
		return( CFSecSecGrpIncTableObj.getRuntimeClassCode() );
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
		allSecGrpInc = null;
		indexByClusterIdx = null;
		indexByGroupIdx = null;
		indexByIncludeIdx = null;
		indexByUIncludeIdx = null;
		List<ICFSecSecGrpIncObj> toForget = new LinkedList<ICFSecSecGrpIncObj>();
		ICFSecSecGrpIncObj cur = null;
		Iterator<ICFSecSecGrpIncObj> iter = members.values().iterator();
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
	 *	CFIntSecGrpIncObj.
	 */
	@Override
	public ICFSecSecGrpIncObj newInstance() {
		ICFSecSecGrpIncObj inst = new CFIntSecGrpIncObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntSecGrpIncObj.
	 */
	@Override
	public ICFSecSecGrpIncEditObj newEditInstance( ICFSecSecGrpIncObj orig ) {
		ICFSecSecGrpIncEditObj edit = new CFIntSecGrpIncEditObj( orig );
		return( edit );
	}

	@Override
	public ICFSecSecGrpIncObj realiseSecGrpInc( ICFSecSecGrpIncObj Obj ) {
		ICFSecSecGrpIncObj obj = Obj;
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecSecGrpIncObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFSecSecGrpIncObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByClusterIdx != null ) {
				ICFSecSecGrpIncByClusterIdxKey keyClusterIdx =
					schema.getCFSecBackingStore().getFactorySecGrpInc().newByClusterIdxKey();
				keyClusterIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj > mapClusterIdx = indexByClusterIdx.get( keyClusterIdx );
				if( mapClusterIdx != null ) {
					mapClusterIdx.remove( keepObj.getPKey() );
					if( mapClusterIdx.size() <= 0 ) {
						indexByClusterIdx.remove( keyClusterIdx );
					}
				}
			}

			if( indexByGroupIdx != null ) {
				ICFSecSecGrpIncByGroupIdxKey keyGroupIdx =
					schema.getCFSecBackingStore().getFactorySecGrpInc().newByGroupIdxKey();
				keyGroupIdx.setRequiredSecGroupId( keepObj.getRequiredSecGroupId() );
				Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj > mapGroupIdx = indexByGroupIdx.get( keyGroupIdx );
				if( mapGroupIdx != null ) {
					mapGroupIdx.remove( keepObj.getPKey() );
					if( mapGroupIdx.size() <= 0 ) {
						indexByGroupIdx.remove( keyGroupIdx );
					}
				}
			}

			if( indexByIncludeIdx != null ) {
				ICFSecSecGrpIncByIncludeIdxKey keyIncludeIdx =
					schema.getCFSecBackingStore().getFactorySecGrpInc().newByIncludeIdxKey();
				keyIncludeIdx.setRequiredIncludeGroupId( keepObj.getRequiredIncludeGroupId() );
				Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj > mapIncludeIdx = indexByIncludeIdx.get( keyIncludeIdx );
				if( mapIncludeIdx != null ) {
					mapIncludeIdx.remove( keepObj.getPKey() );
					if( mapIncludeIdx.size() <= 0 ) {
						indexByIncludeIdx.remove( keyIncludeIdx );
					}
				}
			}

			if( indexByUIncludeIdx != null ) {
				ICFSecSecGrpIncByUIncludeIdxKey keyUIncludeIdx =
					schema.getCFSecBackingStore().getFactorySecGrpInc().newByUIncludeIdxKey();
				keyUIncludeIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyUIncludeIdx.setRequiredSecGroupId( keepObj.getRequiredSecGroupId() );
				keyUIncludeIdx.setRequiredIncludeGroupId( keepObj.getRequiredIncludeGroupId() );
				indexByUIncludeIdx.remove( keyUIncludeIdx );
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByClusterIdx != null ) {
				ICFSecSecGrpIncByClusterIdxKey keyClusterIdx =
					schema.getCFSecBackingStore().getFactorySecGrpInc().newByClusterIdxKey();
				keyClusterIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj > mapClusterIdx = indexByClusterIdx.get( keyClusterIdx );
				if( mapClusterIdx != null ) {
					mapClusterIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByGroupIdx != null ) {
				ICFSecSecGrpIncByGroupIdxKey keyGroupIdx =
					schema.getCFSecBackingStore().getFactorySecGrpInc().newByGroupIdxKey();
				keyGroupIdx.setRequiredSecGroupId( keepObj.getRequiredSecGroupId() );
				Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj > mapGroupIdx = indexByGroupIdx.get( keyGroupIdx );
				if( mapGroupIdx != null ) {
					mapGroupIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByIncludeIdx != null ) {
				ICFSecSecGrpIncByIncludeIdxKey keyIncludeIdx =
					schema.getCFSecBackingStore().getFactorySecGrpInc().newByIncludeIdxKey();
				keyIncludeIdx.setRequiredIncludeGroupId( keepObj.getRequiredIncludeGroupId() );
				Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj > mapIncludeIdx = indexByIncludeIdx.get( keyIncludeIdx );
				if( mapIncludeIdx != null ) {
					mapIncludeIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUIncludeIdx != null ) {
				ICFSecSecGrpIncByUIncludeIdxKey keyUIncludeIdx =
					schema.getCFSecBackingStore().getFactorySecGrpInc().newByUIncludeIdxKey();
				keyUIncludeIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyUIncludeIdx.setRequiredSecGroupId( keepObj.getRequiredSecGroupId() );
				keyUIncludeIdx.setRequiredIncludeGroupId( keepObj.getRequiredIncludeGroupId() );
				indexByUIncludeIdx.put( keyUIncludeIdx, keepObj );
			}

			if( allSecGrpInc != null ) {
				allSecGrpInc.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allSecGrpInc != null ) {
				allSecGrpInc.put( keepObj.getPKey(), keepObj );
			}

			if( indexByClusterIdx != null ) {
				ICFSecSecGrpIncByClusterIdxKey keyClusterIdx =
					schema.getCFSecBackingStore().getFactorySecGrpInc().newByClusterIdxKey();
				keyClusterIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj > mapClusterIdx = indexByClusterIdx.get( keyClusterIdx );
				if( mapClusterIdx != null ) {
					mapClusterIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByGroupIdx != null ) {
				ICFSecSecGrpIncByGroupIdxKey keyGroupIdx =
					schema.getCFSecBackingStore().getFactorySecGrpInc().newByGroupIdxKey();
				keyGroupIdx.setRequiredSecGroupId( keepObj.getRequiredSecGroupId() );
				Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj > mapGroupIdx = indexByGroupIdx.get( keyGroupIdx );
				if( mapGroupIdx != null ) {
					mapGroupIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByIncludeIdx != null ) {
				ICFSecSecGrpIncByIncludeIdxKey keyIncludeIdx =
					schema.getCFSecBackingStore().getFactorySecGrpInc().newByIncludeIdxKey();
				keyIncludeIdx.setRequiredIncludeGroupId( keepObj.getRequiredIncludeGroupId() );
				Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj > mapIncludeIdx = indexByIncludeIdx.get( keyIncludeIdx );
				if( mapIncludeIdx != null ) {
					mapIncludeIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUIncludeIdx != null ) {
				ICFSecSecGrpIncByUIncludeIdxKey keyUIncludeIdx =
					schema.getCFSecBackingStore().getFactorySecGrpInc().newByUIncludeIdxKey();
				keyUIncludeIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyUIncludeIdx.setRequiredSecGroupId( keepObj.getRequiredSecGroupId() );
				keyUIncludeIdx.setRequiredIncludeGroupId( keepObj.getRequiredIncludeGroupId() );
				indexByUIncludeIdx.put( keyUIncludeIdx, keepObj );
			}

		}
		return( keepObj );
	}

	@Override
	public ICFSecSecGrpIncObj createSecGrpInc( ICFSecSecGrpIncObj Obj ) {
		ICFSecSecGrpIncObj obj = Obj;
		ICFSecSecGrpInc rec = obj.getSecGrpIncRec();
		schema.getCFSecBackingStore().getTableSecGrpInc().createSecGrpInc(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFSecSecGrpIncObj readSecGrpInc( CFLibDbKeyHash256 pkey ) {
		return( readSecGrpInc( pkey, false ) );
	}

	@Override
	public ICFSecSecGrpIncObj readSecGrpInc( CFLibDbKeyHash256 pkey, boolean forceRead ) {
		ICFSecSecGrpIncObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFSecSecGrpInc readRec = schema.getCFSecBackingStore().getTableSecGrpInc().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getSecGrpIncTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFSecSecGrpIncObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecSecGrpIncObj readCachedSecGrpInc( CFLibDbKeyHash256 pkey ) {
		ICFSecSecGrpIncObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeSecGrpInc( ICFSecSecGrpIncObj obj )
	{
		final String S_ProcName = "CFIntSecGrpIncTableObj.reallyDeepDisposeSecGrpInc() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecSecGrpIncObj existing = readCachedSecGrpInc( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFSecSecGrpIncByClusterIdxKey keyClusterIdx = schema.getCFSecBackingStore().getFactorySecGrpInc().newByClusterIdxKey();
		keyClusterIdx.setRequiredClusterId( existing.getRequiredClusterId() );

		ICFSecSecGrpIncByGroupIdxKey keyGroupIdx = schema.getCFSecBackingStore().getFactorySecGrpInc().newByGroupIdxKey();
		keyGroupIdx.setRequiredSecGroupId( existing.getRequiredSecGroupId() );

		ICFSecSecGrpIncByIncludeIdxKey keyIncludeIdx = schema.getCFSecBackingStore().getFactorySecGrpInc().newByIncludeIdxKey();
		keyIncludeIdx.setRequiredIncludeGroupId( existing.getRequiredIncludeGroupId() );

		ICFSecSecGrpIncByUIncludeIdxKey keyUIncludeIdx = schema.getCFSecBackingStore().getFactorySecGrpInc().newByUIncludeIdxKey();
		keyUIncludeIdx.setRequiredClusterId( existing.getRequiredClusterId() );
		keyUIncludeIdx.setRequiredSecGroupId( existing.getRequiredSecGroupId() );
		keyUIncludeIdx.setRequiredIncludeGroupId( existing.getRequiredIncludeGroupId() );



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

		if( indexByIncludeIdx != null ) {
			if( indexByIncludeIdx.containsKey( keyIncludeIdx ) ) {
				indexByIncludeIdx.get( keyIncludeIdx ).remove( pkey );
				if( indexByIncludeIdx.get( keyIncludeIdx ).size() <= 0 ) {
					indexByIncludeIdx.remove( keyIncludeIdx );
				}
			}
		}

		if( indexByUIncludeIdx != null ) {
			indexByUIncludeIdx.remove( keyUIncludeIdx );
		}


	}
	@Override
	public void deepDisposeSecGrpInc( CFLibDbKeyHash256 pkey ) {
		ICFSecSecGrpIncObj obj = readCachedSecGrpInc( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecSecGrpIncObj lockSecGrpInc( CFLibDbKeyHash256 pkey ) {
		ICFSecSecGrpIncObj locked = null;
		ICFSecSecGrpInc lockRec = schema.getCFSecBackingStore().getTableSecGrpInc().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getSecGrpIncTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFSecSecGrpIncObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockSecGrpInc", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFSecSecGrpIncObj> readAllSecGrpInc() {
		return( readAllSecGrpInc( false ) );
	}

	@Override
	public List<ICFSecSecGrpIncObj> readAllSecGrpInc( boolean forceRead ) {
		final String S_ProcName = "readAllSecGrpInc";
		if( ( allSecGrpInc == null ) || forceRead ) {
			Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj> map = new HashMap<CFLibDbKeyHash256,ICFSecSecGrpIncObj>();
			allSecGrpInc = map;
			ICFSecSecGrpInc[] recList = schema.getCFSecBackingStore().getTableSecGrpInc().readAllDerived( null );
			ICFSecSecGrpInc rec;
			ICFSecSecGrpIncObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecGrpIncObj realised = (ICFSecSecGrpIncObj)obj.realise();
			}
		}
		int len = allSecGrpInc.size();
		ICFSecSecGrpIncObj arr[] = new ICFSecSecGrpIncObj[len];
		Iterator<ICFSecSecGrpIncObj> valIter = allSecGrpInc.values().iterator();
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
		ArrayList<ICFSecSecGrpIncObj> arrayList = new ArrayList<ICFSecSecGrpIncObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecGrpIncObj> cmp = new Comparator<ICFSecSecGrpIncObj>() {
			@Override
			public int compare( ICFSecSecGrpIncObj lhs, ICFSecSecGrpIncObj rhs ) {
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
		List<ICFSecSecGrpIncObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecSecGrpIncObj> readCachedAllSecGrpInc() {
		final String S_ProcName = "readCachedAllSecGrpInc";
		ArrayList<ICFSecSecGrpIncObj> arrayList = new ArrayList<ICFSecSecGrpIncObj>();
		if( allSecGrpInc != null ) {
			int len = allSecGrpInc.size();
			ICFSecSecGrpIncObj arr[] = new ICFSecSecGrpIncObj[len];
			Iterator<ICFSecSecGrpIncObj> valIter = allSecGrpInc.values().iterator();
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
		Comparator<ICFSecSecGrpIncObj> cmp = new Comparator<ICFSecSecGrpIncObj>() {
			public int compare( ICFSecSecGrpIncObj lhs, ICFSecSecGrpIncObj rhs ) {
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
	 *	Return a sorted map of a page of the SecGrpInc-derived instances in the database.
	 *
	 *	@return	List of ICFSecSecGrpIncObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	@Override
	public List<ICFSecSecGrpIncObj> pageAllSecGrpInc(CFLibDbKeyHash256 priorSecGrpIncId )
	{
		final String S_ProcName = "pageAllSecGrpInc";
		Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj> map = new HashMap<CFLibDbKeyHash256,ICFSecSecGrpIncObj>();
		ICFSecSecGrpInc[] recList = schema.getCFSecBackingStore().getTableSecGrpInc().pageAllRec( null,
			priorSecGrpIncId );
		ICFSecSecGrpInc rec;
		ICFSecSecGrpIncObj obj;
		ICFSecSecGrpIncObj realised;
		ArrayList<ICFSecSecGrpIncObj> arrayList = new ArrayList<ICFSecSecGrpIncObj>( recList.length );
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			realised = (ICFSecSecGrpIncObj)obj.realise();
			arrayList.add( realised );
		}
		return( arrayList );
	}

	@Override
	public ICFSecSecGrpIncObj readSecGrpIncByIdIdx( CFLibDbKeyHash256 SecGrpIncId )
	{
		return( readSecGrpIncByIdIdx( SecGrpIncId,
			false ) );
	}

	@Override
	public ICFSecSecGrpIncObj readSecGrpIncByIdIdx( CFLibDbKeyHash256 SecGrpIncId, boolean forceRead )
	{
		ICFSecSecGrpIncObj obj = readSecGrpInc( SecGrpIncId, forceRead );
		return( obj );
	}

	@Override
	public List<ICFSecSecGrpIncObj> readSecGrpIncByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		return( readSecGrpIncByClusterIdx( ClusterId,
			false ) );
	}

	@Override
	public List<ICFSecSecGrpIncObj> readSecGrpIncByClusterIdx( CFLibDbKeyHash256 ClusterId,
		boolean forceRead )
	{
		final String S_ProcName = "readSecGrpIncByClusterIdx";
		ICFSecSecGrpIncByClusterIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpInc().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj> dict;
		if( indexByClusterIdx == null ) {
			indexByClusterIdx = new HashMap< ICFSecSecGrpIncByClusterIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecGrpIncObj > >();
		}
		if( ( ! forceRead ) && indexByClusterIdx.containsKey( key ) ) {
			dict = indexByClusterIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecSecGrpIncObj>();
			ICFSecSecGrpIncObj obj;
			ICFSecSecGrpInc[] recList = schema.getCFSecBackingStore().getTableSecGrpInc().readDerivedByClusterIdx( null,
				ClusterId );
			ICFSecSecGrpInc rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getSecGrpIncTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecGrpIncObj realised = (ICFSecSecGrpIncObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByClusterIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecSecGrpIncObj arr[] = new ICFSecSecGrpIncObj[len];
		Iterator<ICFSecSecGrpIncObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecSecGrpIncObj> arrayList = new ArrayList<ICFSecSecGrpIncObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecGrpIncObj> cmp = new Comparator<ICFSecSecGrpIncObj>() {
			@Override
			public int compare( ICFSecSecGrpIncObj lhs, ICFSecSecGrpIncObj rhs ) {
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
		List<ICFSecSecGrpIncObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecSecGrpIncObj> readSecGrpIncByGroupIdx( CFLibDbKeyHash256 SecGroupId )
	{
		return( readSecGrpIncByGroupIdx( SecGroupId,
			false ) );
	}

	@Override
	public List<ICFSecSecGrpIncObj> readSecGrpIncByGroupIdx( CFLibDbKeyHash256 SecGroupId,
		boolean forceRead )
	{
		final String S_ProcName = "readSecGrpIncByGroupIdx";
		ICFSecSecGrpIncByGroupIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpInc().newByGroupIdxKey();
		key.setRequiredSecGroupId( SecGroupId );
		Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj> dict;
		if( indexByGroupIdx == null ) {
			indexByGroupIdx = new HashMap< ICFSecSecGrpIncByGroupIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecGrpIncObj > >();
		}
		if( ( ! forceRead ) && indexByGroupIdx.containsKey( key ) ) {
			dict = indexByGroupIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecSecGrpIncObj>();
			ICFSecSecGrpIncObj obj;
			ICFSecSecGrpInc[] recList = schema.getCFSecBackingStore().getTableSecGrpInc().readDerivedByGroupIdx( null,
				SecGroupId );
			ICFSecSecGrpInc rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getSecGrpIncTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecGrpIncObj realised = (ICFSecSecGrpIncObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByGroupIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecSecGrpIncObj arr[] = new ICFSecSecGrpIncObj[len];
		Iterator<ICFSecSecGrpIncObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecSecGrpIncObj> arrayList = new ArrayList<ICFSecSecGrpIncObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecGrpIncObj> cmp = new Comparator<ICFSecSecGrpIncObj>() {
			@Override
			public int compare( ICFSecSecGrpIncObj lhs, ICFSecSecGrpIncObj rhs ) {
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
		List<ICFSecSecGrpIncObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecSecGrpIncObj> readSecGrpIncByIncludeIdx( CFLibDbKeyHash256 IncludeGroupId )
	{
		return( readSecGrpIncByIncludeIdx( IncludeGroupId,
			false ) );
	}

	@Override
	public List<ICFSecSecGrpIncObj> readSecGrpIncByIncludeIdx( CFLibDbKeyHash256 IncludeGroupId,
		boolean forceRead )
	{
		final String S_ProcName = "readSecGrpIncByIncludeIdx";
		ICFSecSecGrpIncByIncludeIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpInc().newByIncludeIdxKey();
		key.setRequiredIncludeGroupId( IncludeGroupId );
		Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj> dict;
		if( indexByIncludeIdx == null ) {
			indexByIncludeIdx = new HashMap< ICFSecSecGrpIncByIncludeIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecGrpIncObj > >();
		}
		if( ( ! forceRead ) && indexByIncludeIdx.containsKey( key ) ) {
			dict = indexByIncludeIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecSecGrpIncObj>();
			ICFSecSecGrpIncObj obj;
			ICFSecSecGrpInc[] recList = schema.getCFSecBackingStore().getTableSecGrpInc().readDerivedByIncludeIdx( null,
				IncludeGroupId );
			ICFSecSecGrpInc rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getSecGrpIncTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecGrpIncObj realised = (ICFSecSecGrpIncObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByIncludeIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecSecGrpIncObj arr[] = new ICFSecSecGrpIncObj[len];
		Iterator<ICFSecSecGrpIncObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecSecGrpIncObj> arrayList = new ArrayList<ICFSecSecGrpIncObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecGrpIncObj> cmp = new Comparator<ICFSecSecGrpIncObj>() {
			@Override
			public int compare( ICFSecSecGrpIncObj lhs, ICFSecSecGrpIncObj rhs ) {
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
		List<ICFSecSecGrpIncObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFSecSecGrpIncObj readSecGrpIncByUIncludeIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 SecGroupId,
		CFLibDbKeyHash256 IncludeGroupId )
	{
		return( readSecGrpIncByUIncludeIdx( ClusterId,
			SecGroupId,
			IncludeGroupId,
			false ) );
	}

	@Override
	public ICFSecSecGrpIncObj readSecGrpIncByUIncludeIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 SecGroupId,
		CFLibDbKeyHash256 IncludeGroupId, boolean forceRead )
	{
		if( indexByUIncludeIdx == null ) {
			indexByUIncludeIdx = new HashMap< ICFSecSecGrpIncByUIncludeIdxKey,
				ICFSecSecGrpIncObj >();
		}
		ICFSecSecGrpIncByUIncludeIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpInc().newByUIncludeIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredSecGroupId( SecGroupId );
		key.setRequiredIncludeGroupId( IncludeGroupId );
		ICFSecSecGrpIncObj obj = null;
		if( ( ! forceRead ) && indexByUIncludeIdx.containsKey( key ) ) {
			obj = indexByUIncludeIdx.get( key );
		}
		else {
			ICFSecSecGrpInc rec = schema.getCFSecBackingStore().getTableSecGrpInc().readDerivedByUIncludeIdx( null,
				ClusterId,
				SecGroupId,
				IncludeGroupId );
			if( rec != null ) {
				obj = schema.getSecGrpIncTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFSecSecGrpIncObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecSecGrpIncObj readCachedSecGrpIncByIdIdx( CFLibDbKeyHash256 SecGrpIncId )
	{
		ICFSecSecGrpIncObj obj = null;
		obj = readCachedSecGrpInc( SecGrpIncId );
		return( obj );
	}

	@Override
	public List<ICFSecSecGrpIncObj> readCachedSecGrpIncByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		final String S_ProcName = "readCachedSecGrpIncByClusterIdx";
		ICFSecSecGrpIncByClusterIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpInc().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		ArrayList<ICFSecSecGrpIncObj> arrayList = new ArrayList<ICFSecSecGrpIncObj>();
		if( indexByClusterIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj> dict;
			if( indexByClusterIdx.containsKey( key ) ) {
				dict = indexByClusterIdx.get( key );
				int len = dict.size();
				ICFSecSecGrpIncObj arr[] = new ICFSecSecGrpIncObj[len];
				Iterator<ICFSecSecGrpIncObj> valIter = dict.values().iterator();
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
			ICFSecSecGrpIncObj obj;
			Iterator<ICFSecSecGrpIncObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecSecGrpIncObj> cmp = new Comparator<ICFSecSecGrpIncObj>() {
			@Override
			public int compare( ICFSecSecGrpIncObj lhs, ICFSecSecGrpIncObj rhs ) {
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
	public List<ICFSecSecGrpIncObj> readCachedSecGrpIncByGroupIdx( CFLibDbKeyHash256 SecGroupId )
	{
		final String S_ProcName = "readCachedSecGrpIncByGroupIdx";
		ICFSecSecGrpIncByGroupIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpInc().newByGroupIdxKey();
		key.setRequiredSecGroupId( SecGroupId );
		ArrayList<ICFSecSecGrpIncObj> arrayList = new ArrayList<ICFSecSecGrpIncObj>();
		if( indexByGroupIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj> dict;
			if( indexByGroupIdx.containsKey( key ) ) {
				dict = indexByGroupIdx.get( key );
				int len = dict.size();
				ICFSecSecGrpIncObj arr[] = new ICFSecSecGrpIncObj[len];
				Iterator<ICFSecSecGrpIncObj> valIter = dict.values().iterator();
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
			ICFSecSecGrpIncObj obj;
			Iterator<ICFSecSecGrpIncObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecSecGrpIncObj> cmp = new Comparator<ICFSecSecGrpIncObj>() {
			@Override
			public int compare( ICFSecSecGrpIncObj lhs, ICFSecSecGrpIncObj rhs ) {
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
	public List<ICFSecSecGrpIncObj> readCachedSecGrpIncByIncludeIdx( CFLibDbKeyHash256 IncludeGroupId )
	{
		final String S_ProcName = "readCachedSecGrpIncByIncludeIdx";
		ICFSecSecGrpIncByIncludeIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpInc().newByIncludeIdxKey();
		key.setRequiredIncludeGroupId( IncludeGroupId );
		ArrayList<ICFSecSecGrpIncObj> arrayList = new ArrayList<ICFSecSecGrpIncObj>();
		if( indexByIncludeIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj> dict;
			if( indexByIncludeIdx.containsKey( key ) ) {
				dict = indexByIncludeIdx.get( key );
				int len = dict.size();
				ICFSecSecGrpIncObj arr[] = new ICFSecSecGrpIncObj[len];
				Iterator<ICFSecSecGrpIncObj> valIter = dict.values().iterator();
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
			ICFSecSecGrpIncObj obj;
			Iterator<ICFSecSecGrpIncObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecSecGrpIncObj> cmp = new Comparator<ICFSecSecGrpIncObj>() {
			@Override
			public int compare( ICFSecSecGrpIncObj lhs, ICFSecSecGrpIncObj rhs ) {
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
	public ICFSecSecGrpIncObj readCachedSecGrpIncByUIncludeIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 SecGroupId,
		CFLibDbKeyHash256 IncludeGroupId )
	{
		ICFSecSecGrpIncObj obj = null;
		ICFSecSecGrpIncByUIncludeIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpInc().newByUIncludeIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredSecGroupId( SecGroupId );
		key.setRequiredIncludeGroupId( IncludeGroupId );
		if( indexByUIncludeIdx != null ) {
			if( indexByUIncludeIdx.containsKey( key ) ) {
				obj = indexByUIncludeIdx.get( key );
			}
			else {
				Iterator<ICFSecSecGrpIncObj> valIter = members.values().iterator();
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
			Iterator<ICFSecSecGrpIncObj> valIter = members.values().iterator();
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
	public void deepDisposeSecGrpIncByIdIdx( CFLibDbKeyHash256 SecGrpIncId )
	{
		ICFSecSecGrpIncObj obj = readCachedSecGrpIncByIdIdx( SecGrpIncId );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeSecGrpIncByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		final String S_ProcName = "deepDisposeSecGrpIncByClusterIdx";
		ICFSecSecGrpIncObj obj;
		List<ICFSecSecGrpIncObj> arrayList = readCachedSecGrpIncByClusterIdx( ClusterId );
		if( arrayList != null )  {
			Iterator<ICFSecSecGrpIncObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeSecGrpIncByGroupIdx( CFLibDbKeyHash256 SecGroupId )
	{
		final String S_ProcName = "deepDisposeSecGrpIncByGroupIdx";
		ICFSecSecGrpIncObj obj;
		List<ICFSecSecGrpIncObj> arrayList = readCachedSecGrpIncByGroupIdx( SecGroupId );
		if( arrayList != null )  {
			Iterator<ICFSecSecGrpIncObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeSecGrpIncByIncludeIdx( CFLibDbKeyHash256 IncludeGroupId )
	{
		final String S_ProcName = "deepDisposeSecGrpIncByIncludeIdx";
		ICFSecSecGrpIncObj obj;
		List<ICFSecSecGrpIncObj> arrayList = readCachedSecGrpIncByIncludeIdx( IncludeGroupId );
		if( arrayList != null )  {
			Iterator<ICFSecSecGrpIncObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeSecGrpIncByUIncludeIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 SecGroupId,
		CFLibDbKeyHash256 IncludeGroupId )
	{
		ICFSecSecGrpIncObj obj = readCachedSecGrpIncByUIncludeIdx( ClusterId,
				SecGroupId,
				IncludeGroupId );
		if( obj != null ) {
			obj.forget();
		}
	}

	/**
	 *	Read a page of data as a List of SecGrpInc-derived instances sorted by their primary keys,
	 *	as identified by the duplicate ClusterIdx key attributes.
	 *
	 *	@param	ClusterId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return	A List of SecGrpInc-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecSecGrpIncObj> pageSecGrpIncByClusterIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 priorSecGrpIncId )
	{
		final String S_ProcName = "pageSecGrpIncByClusterIdx";
		ICFSecSecGrpIncByClusterIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpInc().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		List<ICFSecSecGrpIncObj> retList = new LinkedList<ICFSecSecGrpIncObj>();
		ICFSecSecGrpIncObj obj;
		ICFSecSecGrpInc[] recList = schema.getCFSecBackingStore().getTableSecGrpInc().pageRecByClusterIdx( null,
				ClusterId,
			priorSecGrpIncId );
		ICFSecSecGrpInc rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getSecGrpIncTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecSecGrpIncObj realised = (ICFSecSecGrpIncObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	/**
	 *	Read a page of data as a List of SecGrpInc-derived instances sorted by their primary keys,
	 *	as identified by the duplicate GroupIdx key attributes.
	 *
	 *	@param	SecGroupId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return	A List of SecGrpInc-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecSecGrpIncObj> pageSecGrpIncByGroupIdx( CFLibDbKeyHash256 SecGroupId,
		CFLibDbKeyHash256 priorSecGrpIncId )
	{
		final String S_ProcName = "pageSecGrpIncByGroupIdx";
		ICFSecSecGrpIncByGroupIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpInc().newByGroupIdxKey();
		key.setRequiredSecGroupId( SecGroupId );
		List<ICFSecSecGrpIncObj> retList = new LinkedList<ICFSecSecGrpIncObj>();
		ICFSecSecGrpIncObj obj;
		ICFSecSecGrpInc[] recList = schema.getCFSecBackingStore().getTableSecGrpInc().pageRecByGroupIdx( null,
				SecGroupId,
			priorSecGrpIncId );
		ICFSecSecGrpInc rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getSecGrpIncTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecSecGrpIncObj realised = (ICFSecSecGrpIncObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	/**
	 *	Read a page of data as a List of SecGrpInc-derived instances sorted by their primary keys,
	 *	as identified by the duplicate IncludeIdx key attributes.
	 *
	 *	@param	IncludeGroupId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return	A List of SecGrpInc-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecSecGrpIncObj> pageSecGrpIncByIncludeIdx( CFLibDbKeyHash256 IncludeGroupId,
		CFLibDbKeyHash256 priorSecGrpIncId )
	{
		final String S_ProcName = "pageSecGrpIncByIncludeIdx";
		ICFSecSecGrpIncByIncludeIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpInc().newByIncludeIdxKey();
		key.setRequiredIncludeGroupId( IncludeGroupId );
		List<ICFSecSecGrpIncObj> retList = new LinkedList<ICFSecSecGrpIncObj>();
		ICFSecSecGrpIncObj obj;
		ICFSecSecGrpInc[] recList = schema.getCFSecBackingStore().getTableSecGrpInc().pageRecByIncludeIdx( null,
				IncludeGroupId,
			priorSecGrpIncId );
		ICFSecSecGrpInc rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getSecGrpIncTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecSecGrpIncObj realised = (ICFSecSecGrpIncObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	@Override
	public ICFSecSecGrpIncObj updateSecGrpInc( ICFSecSecGrpIncObj Obj ) {
		ICFSecSecGrpIncObj obj = Obj;
		schema.getCFSecBackingStore().getTableSecGrpInc().updateSecGrpInc( null,
			Obj.getSecGrpIncRec() );
		obj = (ICFSecSecGrpIncObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteSecGrpInc( ICFSecSecGrpIncObj Obj ) {
		ICFSecSecGrpIncObj obj = Obj;
		schema.getCFSecBackingStore().getTableSecGrpInc().deleteSecGrpInc( null,
			obj.getSecGrpIncRec() );
		Obj.forget();
	}

	@Override
	public void deleteSecGrpIncByIdIdx( CFLibDbKeyHash256 SecGrpIncId )
	{
		ICFSecSecGrpIncObj obj = readSecGrpInc(SecGrpIncId);
		if( obj != null ) {
			ICFSecSecGrpIncEditObj editObj = (ICFSecSecGrpIncEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFSecSecGrpIncEditObj)obj.beginEdit();
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
		deepDisposeSecGrpIncByIdIdx( SecGrpIncId );
	}

	@Override
	public void deleteSecGrpIncByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		ICFSecSecGrpIncByClusterIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpInc().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		if( indexByClusterIdx == null ) {
			indexByClusterIdx = new HashMap< ICFSecSecGrpIncByClusterIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecGrpIncObj > >();
		}
		if( indexByClusterIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj> dict = indexByClusterIdx.get( key );
			schema.getCFSecBackingStore().getTableSecGrpInc().deleteSecGrpIncByClusterIdx( null,
				ClusterId );
			Iterator<ICFSecSecGrpIncObj> iter = dict.values().iterator();
			ICFSecSecGrpIncObj obj;
			List<ICFSecSecGrpIncObj> toForget = new LinkedList<ICFSecSecGrpIncObj>();
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
			schema.getCFSecBackingStore().getTableSecGrpInc().deleteSecGrpIncByClusterIdx( null,
				ClusterId );
		}
		deepDisposeSecGrpIncByClusterIdx( ClusterId );
	}

	@Override
	public void deleteSecGrpIncByGroupIdx( CFLibDbKeyHash256 SecGroupId )
	{
		ICFSecSecGrpIncByGroupIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpInc().newByGroupIdxKey();
		key.setRequiredSecGroupId( SecGroupId );
		if( indexByGroupIdx == null ) {
			indexByGroupIdx = new HashMap< ICFSecSecGrpIncByGroupIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecGrpIncObj > >();
		}
		if( indexByGroupIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj> dict = indexByGroupIdx.get( key );
			schema.getCFSecBackingStore().getTableSecGrpInc().deleteSecGrpIncByGroupIdx( null,
				SecGroupId );
			Iterator<ICFSecSecGrpIncObj> iter = dict.values().iterator();
			ICFSecSecGrpIncObj obj;
			List<ICFSecSecGrpIncObj> toForget = new LinkedList<ICFSecSecGrpIncObj>();
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
			schema.getCFSecBackingStore().getTableSecGrpInc().deleteSecGrpIncByGroupIdx( null,
				SecGroupId );
		}
		deepDisposeSecGrpIncByGroupIdx( SecGroupId );
	}

	@Override
	public void deleteSecGrpIncByIncludeIdx( CFLibDbKeyHash256 IncludeGroupId )
	{
		ICFSecSecGrpIncByIncludeIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpInc().newByIncludeIdxKey();
		key.setRequiredIncludeGroupId( IncludeGroupId );
		if( indexByIncludeIdx == null ) {
			indexByIncludeIdx = new HashMap< ICFSecSecGrpIncByIncludeIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecGrpIncObj > >();
		}
		if( indexByIncludeIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecSecGrpIncObj> dict = indexByIncludeIdx.get( key );
			schema.getCFSecBackingStore().getTableSecGrpInc().deleteSecGrpIncByIncludeIdx( null,
				IncludeGroupId );
			Iterator<ICFSecSecGrpIncObj> iter = dict.values().iterator();
			ICFSecSecGrpIncObj obj;
			List<ICFSecSecGrpIncObj> toForget = new LinkedList<ICFSecSecGrpIncObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByIncludeIdx.remove( key );
		}
		else {
			schema.getCFSecBackingStore().getTableSecGrpInc().deleteSecGrpIncByIncludeIdx( null,
				IncludeGroupId );
		}
		deepDisposeSecGrpIncByIncludeIdx( IncludeGroupId );
	}

	@Override
	public void deleteSecGrpIncByUIncludeIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 SecGroupId,
		CFLibDbKeyHash256 IncludeGroupId )
	{
		if( indexByUIncludeIdx == null ) {
			indexByUIncludeIdx = new HashMap< ICFSecSecGrpIncByUIncludeIdxKey,
				ICFSecSecGrpIncObj >();
		}
		ICFSecSecGrpIncByUIncludeIdxKey key = schema.getCFSecBackingStore().getFactorySecGrpInc().newByUIncludeIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredSecGroupId( SecGroupId );
		key.setRequiredIncludeGroupId( IncludeGroupId );
		ICFSecSecGrpIncObj obj = null;
		if( indexByUIncludeIdx.containsKey( key ) ) {
			obj = indexByUIncludeIdx.get( key );
			schema.getCFSecBackingStore().getTableSecGrpInc().deleteSecGrpIncByUIncludeIdx( null,
				ClusterId,
				SecGroupId,
				IncludeGroupId );
			obj.forget();
		}
		else {
			schema.getCFSecBackingStore().getTableSecGrpInc().deleteSecGrpIncByUIncludeIdx( null,
				ClusterId,
				SecGroupId,
				IncludeGroupId );
		}
		deepDisposeSecGrpIncByUIncludeIdx( ClusterId,
				SecGroupId,
				IncludeGroupId );
	}
}