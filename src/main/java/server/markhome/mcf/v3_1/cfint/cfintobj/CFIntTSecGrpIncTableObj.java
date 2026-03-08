// Description: Java 25 Table Object implementation for TSecGrpInc.

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

public class CFIntTSecGrpIncTableObj
	implements ICFIntTSecGrpIncTableObj
{
	protected ICFIntSchemaObj schema;
	private Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj> members;
	private Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj> allTSecGrpInc;
	private Map< ICFSecTSecGrpIncByTenantIdxKey,
		Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj > > indexByTenantIdx;
	private Map< ICFSecTSecGrpIncByGroupIdxKey,
		Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj > > indexByGroupIdx;
	private Map< ICFSecTSecGrpIncByIncludeIdxKey,
		Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj > > indexByIncludeIdx;
	private Map< ICFSecTSecGrpIncByUIncludeIdxKey,
		ICFSecTSecGrpIncObj > indexByUIncludeIdx;
	public static String TABLE_NAME = "TSecGrpInc";
	public static String TABLE_DBNAME = "tsecinc";

	public CFIntTSecGrpIncTableObj() {
		schema = null;
		members = new HashMap<CFLibDbKeyHash256, ICFSecTSecGrpIncObj>();
		allTSecGrpInc = null;
		indexByTenantIdx = null;
		indexByGroupIdx = null;
		indexByIncludeIdx = null;
		indexByUIncludeIdx = null;
	}

	public CFIntTSecGrpIncTableObj( ICFSecSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<CFLibDbKeyHash256, ICFSecTSecGrpIncObj>();
		allTSecGrpInc = null;
		indexByTenantIdx = null;
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
		return CFSecTSecGrpIncTableObj.getRuntimeClassCode();
	}	

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	public static int getBackingClassCode() {
		return( CFSecTSecGrpIncTableObj.getBackingClassCode() );
	}

	/**
	 *	Get the runtime class code for this table; this value is only stable after the application is fully initialized.
	 *
	 *	@return runtimeClassCode
	 */
	public static int getRuntimeClassCode() {
		return( CFSecTSecGrpIncTableObj.getRuntimeClassCode() );
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
		allTSecGrpInc = null;
		indexByTenantIdx = null;
		indexByGroupIdx = null;
		indexByIncludeIdx = null;
		indexByUIncludeIdx = null;
		List<ICFSecTSecGrpIncObj> toForget = new LinkedList<ICFSecTSecGrpIncObj>();
		ICFSecTSecGrpIncObj cur = null;
		Iterator<ICFSecTSecGrpIncObj> iter = members.values().iterator();
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
	 *	CFIntTSecGrpIncObj.
	 */
	@Override
	public ICFSecTSecGrpIncObj newInstance() {
		ICFSecTSecGrpIncObj inst = new CFIntTSecGrpIncObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntTSecGrpIncObj.
	 */
	@Override
	public ICFSecTSecGrpIncEditObj newEditInstance( ICFSecTSecGrpIncObj orig ) {
		ICFSecTSecGrpIncEditObj edit = new CFIntTSecGrpIncEditObj( orig );
		return( edit );
	}

	@Override
	public ICFSecTSecGrpIncObj realiseTSecGrpInc( ICFSecTSecGrpIncObj Obj ) {
		ICFSecTSecGrpIncObj obj = Obj;
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecTSecGrpIncObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFSecTSecGrpIncObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByTenantIdx != null ) {
				ICFSecTSecGrpIncByTenantIdxKey keyTenantIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.remove( keepObj.getPKey() );
					if( mapTenantIdx.size() <= 0 ) {
						indexByTenantIdx.remove( keyTenantIdx );
					}
				}
			}

			if( indexByGroupIdx != null ) {
				ICFSecTSecGrpIncByGroupIdxKey keyGroupIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByGroupIdxKey();
				keyGroupIdx.setRequiredTSecGroupId( keepObj.getRequiredTSecGroupId() );
				Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj > mapGroupIdx = indexByGroupIdx.get( keyGroupIdx );
				if( mapGroupIdx != null ) {
					mapGroupIdx.remove( keepObj.getPKey() );
					if( mapGroupIdx.size() <= 0 ) {
						indexByGroupIdx.remove( keyGroupIdx );
					}
				}
			}

			if( indexByIncludeIdx != null ) {
				ICFSecTSecGrpIncByIncludeIdxKey keyIncludeIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByIncludeIdxKey();
				keyIncludeIdx.setRequiredIncludeGroupId( keepObj.getRequiredIncludeGroupId() );
				Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj > mapIncludeIdx = indexByIncludeIdx.get( keyIncludeIdx );
				if( mapIncludeIdx != null ) {
					mapIncludeIdx.remove( keepObj.getPKey() );
					if( mapIncludeIdx.size() <= 0 ) {
						indexByIncludeIdx.remove( keyIncludeIdx );
					}
				}
			}

			if( indexByUIncludeIdx != null ) {
				ICFSecTSecGrpIncByUIncludeIdxKey keyUIncludeIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByUIncludeIdxKey();
				keyUIncludeIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				keyUIncludeIdx.setRequiredTSecGroupId( keepObj.getRequiredTSecGroupId() );
				keyUIncludeIdx.setRequiredIncludeGroupId( keepObj.getRequiredIncludeGroupId() );
				indexByUIncludeIdx.remove( keyUIncludeIdx );
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByTenantIdx != null ) {
				ICFSecTSecGrpIncByTenantIdxKey keyTenantIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByGroupIdx != null ) {
				ICFSecTSecGrpIncByGroupIdxKey keyGroupIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByGroupIdxKey();
				keyGroupIdx.setRequiredTSecGroupId( keepObj.getRequiredTSecGroupId() );
				Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj > mapGroupIdx = indexByGroupIdx.get( keyGroupIdx );
				if( mapGroupIdx != null ) {
					mapGroupIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByIncludeIdx != null ) {
				ICFSecTSecGrpIncByIncludeIdxKey keyIncludeIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByIncludeIdxKey();
				keyIncludeIdx.setRequiredIncludeGroupId( keepObj.getRequiredIncludeGroupId() );
				Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj > mapIncludeIdx = indexByIncludeIdx.get( keyIncludeIdx );
				if( mapIncludeIdx != null ) {
					mapIncludeIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUIncludeIdx != null ) {
				ICFSecTSecGrpIncByUIncludeIdxKey keyUIncludeIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByUIncludeIdxKey();
				keyUIncludeIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				keyUIncludeIdx.setRequiredTSecGroupId( keepObj.getRequiredTSecGroupId() );
				keyUIncludeIdx.setRequiredIncludeGroupId( keepObj.getRequiredIncludeGroupId() );
				indexByUIncludeIdx.put( keyUIncludeIdx, keepObj );
			}

			if( allTSecGrpInc != null ) {
				allTSecGrpInc.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allTSecGrpInc != null ) {
				allTSecGrpInc.put( keepObj.getPKey(), keepObj );
			}

			if( indexByTenantIdx != null ) {
				ICFSecTSecGrpIncByTenantIdxKey keyTenantIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByGroupIdx != null ) {
				ICFSecTSecGrpIncByGroupIdxKey keyGroupIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByGroupIdxKey();
				keyGroupIdx.setRequiredTSecGroupId( keepObj.getRequiredTSecGroupId() );
				Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj > mapGroupIdx = indexByGroupIdx.get( keyGroupIdx );
				if( mapGroupIdx != null ) {
					mapGroupIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByIncludeIdx != null ) {
				ICFSecTSecGrpIncByIncludeIdxKey keyIncludeIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByIncludeIdxKey();
				keyIncludeIdx.setRequiredIncludeGroupId( keepObj.getRequiredIncludeGroupId() );
				Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj > mapIncludeIdx = indexByIncludeIdx.get( keyIncludeIdx );
				if( mapIncludeIdx != null ) {
					mapIncludeIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUIncludeIdx != null ) {
				ICFSecTSecGrpIncByUIncludeIdxKey keyUIncludeIdx =
					schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByUIncludeIdxKey();
				keyUIncludeIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				keyUIncludeIdx.setRequiredTSecGroupId( keepObj.getRequiredTSecGroupId() );
				keyUIncludeIdx.setRequiredIncludeGroupId( keepObj.getRequiredIncludeGroupId() );
				indexByUIncludeIdx.put( keyUIncludeIdx, keepObj );
			}

		}
		return( keepObj );
	}

	@Override
	public ICFSecTSecGrpIncObj createTSecGrpInc( ICFSecTSecGrpIncObj Obj ) {
		ICFSecTSecGrpIncObj obj = Obj;
		ICFSecTSecGrpInc rec = obj.getTSecGrpIncRec();
		schema.getCFSecBackingStore().getTableTSecGrpInc().createTSecGrpInc(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFSecTSecGrpIncObj readTSecGrpInc( CFLibDbKeyHash256 pkey ) {
		return( readTSecGrpInc( pkey, false ) );
	}

	@Override
	public ICFSecTSecGrpIncObj readTSecGrpInc( CFLibDbKeyHash256 pkey, boolean forceRead ) {
		ICFSecTSecGrpIncObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFSecTSecGrpInc readRec = schema.getCFSecBackingStore().getTableTSecGrpInc().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getTSecGrpIncTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFSecTSecGrpIncObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecTSecGrpIncObj readCachedTSecGrpInc( CFLibDbKeyHash256 pkey ) {
		ICFSecTSecGrpIncObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeTSecGrpInc( ICFSecTSecGrpIncObj obj )
	{
		final String S_ProcName = "CFIntTSecGrpIncTableObj.reallyDeepDisposeTSecGrpInc() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecTSecGrpIncObj existing = readCachedTSecGrpInc( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFSecTSecGrpIncByTenantIdxKey keyTenantIdx = schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByTenantIdxKey();
		keyTenantIdx.setRequiredTenantId( existing.getRequiredTenantId() );

		ICFSecTSecGrpIncByGroupIdxKey keyGroupIdx = schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByGroupIdxKey();
		keyGroupIdx.setRequiredTSecGroupId( existing.getRequiredTSecGroupId() );

		ICFSecTSecGrpIncByIncludeIdxKey keyIncludeIdx = schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByIncludeIdxKey();
		keyIncludeIdx.setRequiredIncludeGroupId( existing.getRequiredIncludeGroupId() );

		ICFSecTSecGrpIncByUIncludeIdxKey keyUIncludeIdx = schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByUIncludeIdxKey();
		keyUIncludeIdx.setRequiredTenantId( existing.getRequiredTenantId() );
		keyUIncludeIdx.setRequiredTSecGroupId( existing.getRequiredTSecGroupId() );
		keyUIncludeIdx.setRequiredIncludeGroupId( existing.getRequiredIncludeGroupId() );



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
	public void deepDisposeTSecGrpInc( CFLibDbKeyHash256 pkey ) {
		ICFSecTSecGrpIncObj obj = readCachedTSecGrpInc( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecTSecGrpIncObj lockTSecGrpInc( CFLibDbKeyHash256 pkey ) {
		ICFSecTSecGrpIncObj locked = null;
		ICFSecTSecGrpInc lockRec = schema.getCFSecBackingStore().getTableTSecGrpInc().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getTSecGrpIncTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFSecTSecGrpIncObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockTSecGrpInc", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFSecTSecGrpIncObj> readAllTSecGrpInc() {
		return( readAllTSecGrpInc( false ) );
	}

	@Override
	public List<ICFSecTSecGrpIncObj> readAllTSecGrpInc( boolean forceRead ) {
		final String S_ProcName = "readAllTSecGrpInc";
		if( ( allTSecGrpInc == null ) || forceRead ) {
			Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj> map = new HashMap<CFLibDbKeyHash256,ICFSecTSecGrpIncObj>();
			allTSecGrpInc = map;
			ICFSecTSecGrpInc[] recList = schema.getCFSecBackingStore().getTableTSecGrpInc().readAllDerived( null );
			ICFSecTSecGrpInc rec;
			ICFSecTSecGrpIncObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecTSecGrpIncObj realised = (ICFSecTSecGrpIncObj)obj.realise();
			}
		}
		int len = allTSecGrpInc.size();
		ICFSecTSecGrpIncObj arr[] = new ICFSecTSecGrpIncObj[len];
		Iterator<ICFSecTSecGrpIncObj> valIter = allTSecGrpInc.values().iterator();
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
		ArrayList<ICFSecTSecGrpIncObj> arrayList = new ArrayList<ICFSecTSecGrpIncObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecTSecGrpIncObj> cmp = new Comparator<ICFSecTSecGrpIncObj>() {
			@Override
			public int compare( ICFSecTSecGrpIncObj lhs, ICFSecTSecGrpIncObj rhs ) {
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
		List<ICFSecTSecGrpIncObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecTSecGrpIncObj> readCachedAllTSecGrpInc() {
		final String S_ProcName = "readCachedAllTSecGrpInc";
		ArrayList<ICFSecTSecGrpIncObj> arrayList = new ArrayList<ICFSecTSecGrpIncObj>();
		if( allTSecGrpInc != null ) {
			int len = allTSecGrpInc.size();
			ICFSecTSecGrpIncObj arr[] = new ICFSecTSecGrpIncObj[len];
			Iterator<ICFSecTSecGrpIncObj> valIter = allTSecGrpInc.values().iterator();
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
		Comparator<ICFSecTSecGrpIncObj> cmp = new Comparator<ICFSecTSecGrpIncObj>() {
			public int compare( ICFSecTSecGrpIncObj lhs, ICFSecTSecGrpIncObj rhs ) {
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
	 *	Return a sorted map of a page of the TSecGrpInc-derived instances in the database.
	 *
	 *	@return	List of ICFSecTSecGrpIncObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	@Override
	public List<ICFSecTSecGrpIncObj> pageAllTSecGrpInc(CFLibDbKeyHash256 priorTSecGrpIncId )
	{
		final String S_ProcName = "pageAllTSecGrpInc";
		Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj> map = new HashMap<CFLibDbKeyHash256,ICFSecTSecGrpIncObj>();
		ICFSecTSecGrpInc[] recList = schema.getCFSecBackingStore().getTableTSecGrpInc().pageAllRec( null,
			priorTSecGrpIncId );
		ICFSecTSecGrpInc rec;
		ICFSecTSecGrpIncObj obj;
		ICFSecTSecGrpIncObj realised;
		ArrayList<ICFSecTSecGrpIncObj> arrayList = new ArrayList<ICFSecTSecGrpIncObj>( recList.length );
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			realised = (ICFSecTSecGrpIncObj)obj.realise();
			arrayList.add( realised );
		}
		return( arrayList );
	}

	@Override
	public ICFSecTSecGrpIncObj readTSecGrpIncByIdIdx( CFLibDbKeyHash256 TSecGrpIncId )
	{
		return( readTSecGrpIncByIdIdx( TSecGrpIncId,
			false ) );
	}

	@Override
	public ICFSecTSecGrpIncObj readTSecGrpIncByIdIdx( CFLibDbKeyHash256 TSecGrpIncId, boolean forceRead )
	{
		ICFSecTSecGrpIncObj obj = readTSecGrpInc( TSecGrpIncId, forceRead );
		return( obj );
	}

	@Override
	public List<ICFSecTSecGrpIncObj> readTSecGrpIncByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		return( readTSecGrpIncByTenantIdx( TenantId,
			false ) );
	}

	@Override
	public List<ICFSecTSecGrpIncObj> readTSecGrpIncByTenantIdx( CFLibDbKeyHash256 TenantId,
		boolean forceRead )
	{
		final String S_ProcName = "readTSecGrpIncByTenantIdx";
		ICFSecTSecGrpIncByTenantIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj> dict;
		if( indexByTenantIdx == null ) {
			indexByTenantIdx = new HashMap< ICFSecTSecGrpIncByTenantIdxKey,
				Map< CFLibDbKeyHash256, ICFSecTSecGrpIncObj > >();
		}
		if( ( ! forceRead ) && indexByTenantIdx.containsKey( key ) ) {
			dict = indexByTenantIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecTSecGrpIncObj>();
			ICFSecTSecGrpIncObj obj;
			ICFSecTSecGrpInc[] recList = schema.getCFSecBackingStore().getTableTSecGrpInc().readDerivedByTenantIdx( null,
				TenantId );
			ICFSecTSecGrpInc rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getTSecGrpIncTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecTSecGrpIncObj realised = (ICFSecTSecGrpIncObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByTenantIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecTSecGrpIncObj arr[] = new ICFSecTSecGrpIncObj[len];
		Iterator<ICFSecTSecGrpIncObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecTSecGrpIncObj> arrayList = new ArrayList<ICFSecTSecGrpIncObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecTSecGrpIncObj> cmp = new Comparator<ICFSecTSecGrpIncObj>() {
			@Override
			public int compare( ICFSecTSecGrpIncObj lhs, ICFSecTSecGrpIncObj rhs ) {
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
		List<ICFSecTSecGrpIncObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecTSecGrpIncObj> readTSecGrpIncByGroupIdx( CFLibDbKeyHash256 TSecGroupId )
	{
		return( readTSecGrpIncByGroupIdx( TSecGroupId,
			false ) );
	}

	@Override
	public List<ICFSecTSecGrpIncObj> readTSecGrpIncByGroupIdx( CFLibDbKeyHash256 TSecGroupId,
		boolean forceRead )
	{
		final String S_ProcName = "readTSecGrpIncByGroupIdx";
		ICFSecTSecGrpIncByGroupIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByGroupIdxKey();
		key.setRequiredTSecGroupId( TSecGroupId );
		Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj> dict;
		if( indexByGroupIdx == null ) {
			indexByGroupIdx = new HashMap< ICFSecTSecGrpIncByGroupIdxKey,
				Map< CFLibDbKeyHash256, ICFSecTSecGrpIncObj > >();
		}
		if( ( ! forceRead ) && indexByGroupIdx.containsKey( key ) ) {
			dict = indexByGroupIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecTSecGrpIncObj>();
			ICFSecTSecGrpIncObj obj;
			ICFSecTSecGrpInc[] recList = schema.getCFSecBackingStore().getTableTSecGrpInc().readDerivedByGroupIdx( null,
				TSecGroupId );
			ICFSecTSecGrpInc rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getTSecGrpIncTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecTSecGrpIncObj realised = (ICFSecTSecGrpIncObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByGroupIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecTSecGrpIncObj arr[] = new ICFSecTSecGrpIncObj[len];
		Iterator<ICFSecTSecGrpIncObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecTSecGrpIncObj> arrayList = new ArrayList<ICFSecTSecGrpIncObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecTSecGrpIncObj> cmp = new Comparator<ICFSecTSecGrpIncObj>() {
			@Override
			public int compare( ICFSecTSecGrpIncObj lhs, ICFSecTSecGrpIncObj rhs ) {
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
		List<ICFSecTSecGrpIncObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecTSecGrpIncObj> readTSecGrpIncByIncludeIdx( CFLibDbKeyHash256 IncludeGroupId )
	{
		return( readTSecGrpIncByIncludeIdx( IncludeGroupId,
			false ) );
	}

	@Override
	public List<ICFSecTSecGrpIncObj> readTSecGrpIncByIncludeIdx( CFLibDbKeyHash256 IncludeGroupId,
		boolean forceRead )
	{
		final String S_ProcName = "readTSecGrpIncByIncludeIdx";
		ICFSecTSecGrpIncByIncludeIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByIncludeIdxKey();
		key.setRequiredIncludeGroupId( IncludeGroupId );
		Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj> dict;
		if( indexByIncludeIdx == null ) {
			indexByIncludeIdx = new HashMap< ICFSecTSecGrpIncByIncludeIdxKey,
				Map< CFLibDbKeyHash256, ICFSecTSecGrpIncObj > >();
		}
		if( ( ! forceRead ) && indexByIncludeIdx.containsKey( key ) ) {
			dict = indexByIncludeIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecTSecGrpIncObj>();
			ICFSecTSecGrpIncObj obj;
			ICFSecTSecGrpInc[] recList = schema.getCFSecBackingStore().getTableTSecGrpInc().readDerivedByIncludeIdx( null,
				IncludeGroupId );
			ICFSecTSecGrpInc rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getTSecGrpIncTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecTSecGrpIncObj realised = (ICFSecTSecGrpIncObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByIncludeIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecTSecGrpIncObj arr[] = new ICFSecTSecGrpIncObj[len];
		Iterator<ICFSecTSecGrpIncObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecTSecGrpIncObj> arrayList = new ArrayList<ICFSecTSecGrpIncObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecTSecGrpIncObj> cmp = new Comparator<ICFSecTSecGrpIncObj>() {
			@Override
			public int compare( ICFSecTSecGrpIncObj lhs, ICFSecTSecGrpIncObj rhs ) {
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
		List<ICFSecTSecGrpIncObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFSecTSecGrpIncObj readTSecGrpIncByUIncludeIdx( CFLibDbKeyHash256 TenantId,
		CFLibDbKeyHash256 TSecGroupId,
		CFLibDbKeyHash256 IncludeGroupId )
	{
		return( readTSecGrpIncByUIncludeIdx( TenantId,
			TSecGroupId,
			IncludeGroupId,
			false ) );
	}

	@Override
	public ICFSecTSecGrpIncObj readTSecGrpIncByUIncludeIdx( CFLibDbKeyHash256 TenantId,
		CFLibDbKeyHash256 TSecGroupId,
		CFLibDbKeyHash256 IncludeGroupId, boolean forceRead )
	{
		if( indexByUIncludeIdx == null ) {
			indexByUIncludeIdx = new HashMap< ICFSecTSecGrpIncByUIncludeIdxKey,
				ICFSecTSecGrpIncObj >();
		}
		ICFSecTSecGrpIncByUIncludeIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByUIncludeIdxKey();
		key.setRequiredTenantId( TenantId );
		key.setRequiredTSecGroupId( TSecGroupId );
		key.setRequiredIncludeGroupId( IncludeGroupId );
		ICFSecTSecGrpIncObj obj = null;
		if( ( ! forceRead ) && indexByUIncludeIdx.containsKey( key ) ) {
			obj = indexByUIncludeIdx.get( key );
		}
		else {
			ICFSecTSecGrpInc rec = schema.getCFSecBackingStore().getTableTSecGrpInc().readDerivedByUIncludeIdx( null,
				TenantId,
				TSecGroupId,
				IncludeGroupId );
			if( rec != null ) {
				obj = schema.getTSecGrpIncTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFSecTSecGrpIncObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecTSecGrpIncObj readCachedTSecGrpIncByIdIdx( CFLibDbKeyHash256 TSecGrpIncId )
	{
		ICFSecTSecGrpIncObj obj = null;
		obj = readCachedTSecGrpInc( TSecGrpIncId );
		return( obj );
	}

	@Override
	public List<ICFSecTSecGrpIncObj> readCachedTSecGrpIncByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		final String S_ProcName = "readCachedTSecGrpIncByTenantIdx";
		ICFSecTSecGrpIncByTenantIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		ArrayList<ICFSecTSecGrpIncObj> arrayList = new ArrayList<ICFSecTSecGrpIncObj>();
		if( indexByTenantIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj> dict;
			if( indexByTenantIdx.containsKey( key ) ) {
				dict = indexByTenantIdx.get( key );
				int len = dict.size();
				ICFSecTSecGrpIncObj arr[] = new ICFSecTSecGrpIncObj[len];
				Iterator<ICFSecTSecGrpIncObj> valIter = dict.values().iterator();
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
			ICFSecTSecGrpIncObj obj;
			Iterator<ICFSecTSecGrpIncObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecTSecGrpIncObj> cmp = new Comparator<ICFSecTSecGrpIncObj>() {
			@Override
			public int compare( ICFSecTSecGrpIncObj lhs, ICFSecTSecGrpIncObj rhs ) {
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
	public List<ICFSecTSecGrpIncObj> readCachedTSecGrpIncByGroupIdx( CFLibDbKeyHash256 TSecGroupId )
	{
		final String S_ProcName = "readCachedTSecGrpIncByGroupIdx";
		ICFSecTSecGrpIncByGroupIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByGroupIdxKey();
		key.setRequiredTSecGroupId( TSecGroupId );
		ArrayList<ICFSecTSecGrpIncObj> arrayList = new ArrayList<ICFSecTSecGrpIncObj>();
		if( indexByGroupIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj> dict;
			if( indexByGroupIdx.containsKey( key ) ) {
				dict = indexByGroupIdx.get( key );
				int len = dict.size();
				ICFSecTSecGrpIncObj arr[] = new ICFSecTSecGrpIncObj[len];
				Iterator<ICFSecTSecGrpIncObj> valIter = dict.values().iterator();
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
			ICFSecTSecGrpIncObj obj;
			Iterator<ICFSecTSecGrpIncObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecTSecGrpIncObj> cmp = new Comparator<ICFSecTSecGrpIncObj>() {
			@Override
			public int compare( ICFSecTSecGrpIncObj lhs, ICFSecTSecGrpIncObj rhs ) {
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
	public List<ICFSecTSecGrpIncObj> readCachedTSecGrpIncByIncludeIdx( CFLibDbKeyHash256 IncludeGroupId )
	{
		final String S_ProcName = "readCachedTSecGrpIncByIncludeIdx";
		ICFSecTSecGrpIncByIncludeIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByIncludeIdxKey();
		key.setRequiredIncludeGroupId( IncludeGroupId );
		ArrayList<ICFSecTSecGrpIncObj> arrayList = new ArrayList<ICFSecTSecGrpIncObj>();
		if( indexByIncludeIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj> dict;
			if( indexByIncludeIdx.containsKey( key ) ) {
				dict = indexByIncludeIdx.get( key );
				int len = dict.size();
				ICFSecTSecGrpIncObj arr[] = new ICFSecTSecGrpIncObj[len];
				Iterator<ICFSecTSecGrpIncObj> valIter = dict.values().iterator();
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
			ICFSecTSecGrpIncObj obj;
			Iterator<ICFSecTSecGrpIncObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecTSecGrpIncObj> cmp = new Comparator<ICFSecTSecGrpIncObj>() {
			@Override
			public int compare( ICFSecTSecGrpIncObj lhs, ICFSecTSecGrpIncObj rhs ) {
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
	public ICFSecTSecGrpIncObj readCachedTSecGrpIncByUIncludeIdx( CFLibDbKeyHash256 TenantId,
		CFLibDbKeyHash256 TSecGroupId,
		CFLibDbKeyHash256 IncludeGroupId )
	{
		ICFSecTSecGrpIncObj obj = null;
		ICFSecTSecGrpIncByUIncludeIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByUIncludeIdxKey();
		key.setRequiredTenantId( TenantId );
		key.setRequiredTSecGroupId( TSecGroupId );
		key.setRequiredIncludeGroupId( IncludeGroupId );
		if( indexByUIncludeIdx != null ) {
			if( indexByUIncludeIdx.containsKey( key ) ) {
				obj = indexByUIncludeIdx.get( key );
			}
			else {
				Iterator<ICFSecTSecGrpIncObj> valIter = members.values().iterator();
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
			Iterator<ICFSecTSecGrpIncObj> valIter = members.values().iterator();
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
	public void deepDisposeTSecGrpIncByIdIdx( CFLibDbKeyHash256 TSecGrpIncId )
	{
		ICFSecTSecGrpIncObj obj = readCachedTSecGrpIncByIdIdx( TSecGrpIncId );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeTSecGrpIncByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		final String S_ProcName = "deepDisposeTSecGrpIncByTenantIdx";
		ICFSecTSecGrpIncObj obj;
		List<ICFSecTSecGrpIncObj> arrayList = readCachedTSecGrpIncByTenantIdx( TenantId );
		if( arrayList != null )  {
			Iterator<ICFSecTSecGrpIncObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeTSecGrpIncByGroupIdx( CFLibDbKeyHash256 TSecGroupId )
	{
		final String S_ProcName = "deepDisposeTSecGrpIncByGroupIdx";
		ICFSecTSecGrpIncObj obj;
		List<ICFSecTSecGrpIncObj> arrayList = readCachedTSecGrpIncByGroupIdx( TSecGroupId );
		if( arrayList != null )  {
			Iterator<ICFSecTSecGrpIncObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeTSecGrpIncByIncludeIdx( CFLibDbKeyHash256 IncludeGroupId )
	{
		final String S_ProcName = "deepDisposeTSecGrpIncByIncludeIdx";
		ICFSecTSecGrpIncObj obj;
		List<ICFSecTSecGrpIncObj> arrayList = readCachedTSecGrpIncByIncludeIdx( IncludeGroupId );
		if( arrayList != null )  {
			Iterator<ICFSecTSecGrpIncObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeTSecGrpIncByUIncludeIdx( CFLibDbKeyHash256 TenantId,
		CFLibDbKeyHash256 TSecGroupId,
		CFLibDbKeyHash256 IncludeGroupId )
	{
		ICFSecTSecGrpIncObj obj = readCachedTSecGrpIncByUIncludeIdx( TenantId,
				TSecGroupId,
				IncludeGroupId );
		if( obj != null ) {
			obj.forget();
		}
	}

	/**
	 *	Read a page of data as a List of TSecGrpInc-derived instances sorted by their primary keys,
	 *	as identified by the duplicate TenantIdx key attributes.
	 *
	 *	@param	TenantId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return	A List of TSecGrpInc-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecTSecGrpIncObj> pageTSecGrpIncByTenantIdx( CFLibDbKeyHash256 TenantId,
		CFLibDbKeyHash256 priorTSecGrpIncId )
	{
		final String S_ProcName = "pageTSecGrpIncByTenantIdx";
		ICFSecTSecGrpIncByTenantIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		List<ICFSecTSecGrpIncObj> retList = new LinkedList<ICFSecTSecGrpIncObj>();
		ICFSecTSecGrpIncObj obj;
		ICFSecTSecGrpInc[] recList = schema.getCFSecBackingStore().getTableTSecGrpInc().pageRecByTenantIdx( null,
				TenantId,
			priorTSecGrpIncId );
		ICFSecTSecGrpInc rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getTSecGrpIncTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecTSecGrpIncObj realised = (ICFSecTSecGrpIncObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	/**
	 *	Read a page of data as a List of TSecGrpInc-derived instances sorted by their primary keys,
	 *	as identified by the duplicate GroupIdx key attributes.
	 *
	 *	@param	TSecGroupId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return	A List of TSecGrpInc-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecTSecGrpIncObj> pageTSecGrpIncByGroupIdx( CFLibDbKeyHash256 TSecGroupId,
		CFLibDbKeyHash256 priorTSecGrpIncId )
	{
		final String S_ProcName = "pageTSecGrpIncByGroupIdx";
		ICFSecTSecGrpIncByGroupIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByGroupIdxKey();
		key.setRequiredTSecGroupId( TSecGroupId );
		List<ICFSecTSecGrpIncObj> retList = new LinkedList<ICFSecTSecGrpIncObj>();
		ICFSecTSecGrpIncObj obj;
		ICFSecTSecGrpInc[] recList = schema.getCFSecBackingStore().getTableTSecGrpInc().pageRecByGroupIdx( null,
				TSecGroupId,
			priorTSecGrpIncId );
		ICFSecTSecGrpInc rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getTSecGrpIncTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecTSecGrpIncObj realised = (ICFSecTSecGrpIncObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	/**
	 *	Read a page of data as a List of TSecGrpInc-derived instances sorted by their primary keys,
	 *	as identified by the duplicate IncludeIdx key attributes.
	 *
	 *	@param	IncludeGroupId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return	A List of TSecGrpInc-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecTSecGrpIncObj> pageTSecGrpIncByIncludeIdx( CFLibDbKeyHash256 IncludeGroupId,
		CFLibDbKeyHash256 priorTSecGrpIncId )
	{
		final String S_ProcName = "pageTSecGrpIncByIncludeIdx";
		ICFSecTSecGrpIncByIncludeIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByIncludeIdxKey();
		key.setRequiredIncludeGroupId( IncludeGroupId );
		List<ICFSecTSecGrpIncObj> retList = new LinkedList<ICFSecTSecGrpIncObj>();
		ICFSecTSecGrpIncObj obj;
		ICFSecTSecGrpInc[] recList = schema.getCFSecBackingStore().getTableTSecGrpInc().pageRecByIncludeIdx( null,
				IncludeGroupId,
			priorTSecGrpIncId );
		ICFSecTSecGrpInc rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getTSecGrpIncTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecTSecGrpIncObj realised = (ICFSecTSecGrpIncObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	@Override
	public ICFSecTSecGrpIncObj updateTSecGrpInc( ICFSecTSecGrpIncObj Obj ) {
		ICFSecTSecGrpIncObj obj = Obj;
		schema.getCFSecBackingStore().getTableTSecGrpInc().updateTSecGrpInc( null,
			Obj.getTSecGrpIncRec() );
		obj = (ICFSecTSecGrpIncObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteTSecGrpInc( ICFSecTSecGrpIncObj Obj ) {
		ICFSecTSecGrpIncObj obj = Obj;
		schema.getCFSecBackingStore().getTableTSecGrpInc().deleteTSecGrpInc( null,
			obj.getTSecGrpIncRec() );
		Obj.forget();
	}

	@Override
	public void deleteTSecGrpIncByIdIdx( CFLibDbKeyHash256 TSecGrpIncId )
	{
		ICFSecTSecGrpIncObj obj = readTSecGrpInc(TSecGrpIncId);
		if( obj != null ) {
			ICFSecTSecGrpIncEditObj editObj = (ICFSecTSecGrpIncEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFSecTSecGrpIncEditObj)obj.beginEdit();
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
		deepDisposeTSecGrpIncByIdIdx( TSecGrpIncId );
	}

	@Override
	public void deleteTSecGrpIncByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		ICFSecTSecGrpIncByTenantIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		if( indexByTenantIdx == null ) {
			indexByTenantIdx = new HashMap< ICFSecTSecGrpIncByTenantIdxKey,
				Map< CFLibDbKeyHash256, ICFSecTSecGrpIncObj > >();
		}
		if( indexByTenantIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj> dict = indexByTenantIdx.get( key );
			schema.getCFSecBackingStore().getTableTSecGrpInc().deleteTSecGrpIncByTenantIdx( null,
				TenantId );
			Iterator<ICFSecTSecGrpIncObj> iter = dict.values().iterator();
			ICFSecTSecGrpIncObj obj;
			List<ICFSecTSecGrpIncObj> toForget = new LinkedList<ICFSecTSecGrpIncObj>();
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
			schema.getCFSecBackingStore().getTableTSecGrpInc().deleteTSecGrpIncByTenantIdx( null,
				TenantId );
		}
		deepDisposeTSecGrpIncByTenantIdx( TenantId );
	}

	@Override
	public void deleteTSecGrpIncByGroupIdx( CFLibDbKeyHash256 TSecGroupId )
	{
		ICFSecTSecGrpIncByGroupIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByGroupIdxKey();
		key.setRequiredTSecGroupId( TSecGroupId );
		if( indexByGroupIdx == null ) {
			indexByGroupIdx = new HashMap< ICFSecTSecGrpIncByGroupIdxKey,
				Map< CFLibDbKeyHash256, ICFSecTSecGrpIncObj > >();
		}
		if( indexByGroupIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj> dict = indexByGroupIdx.get( key );
			schema.getCFSecBackingStore().getTableTSecGrpInc().deleteTSecGrpIncByGroupIdx( null,
				TSecGroupId );
			Iterator<ICFSecTSecGrpIncObj> iter = dict.values().iterator();
			ICFSecTSecGrpIncObj obj;
			List<ICFSecTSecGrpIncObj> toForget = new LinkedList<ICFSecTSecGrpIncObj>();
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
			schema.getCFSecBackingStore().getTableTSecGrpInc().deleteTSecGrpIncByGroupIdx( null,
				TSecGroupId );
		}
		deepDisposeTSecGrpIncByGroupIdx( TSecGroupId );
	}

	@Override
	public void deleteTSecGrpIncByIncludeIdx( CFLibDbKeyHash256 IncludeGroupId )
	{
		ICFSecTSecGrpIncByIncludeIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByIncludeIdxKey();
		key.setRequiredIncludeGroupId( IncludeGroupId );
		if( indexByIncludeIdx == null ) {
			indexByIncludeIdx = new HashMap< ICFSecTSecGrpIncByIncludeIdxKey,
				Map< CFLibDbKeyHash256, ICFSecTSecGrpIncObj > >();
		}
		if( indexByIncludeIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecTSecGrpIncObj> dict = indexByIncludeIdx.get( key );
			schema.getCFSecBackingStore().getTableTSecGrpInc().deleteTSecGrpIncByIncludeIdx( null,
				IncludeGroupId );
			Iterator<ICFSecTSecGrpIncObj> iter = dict.values().iterator();
			ICFSecTSecGrpIncObj obj;
			List<ICFSecTSecGrpIncObj> toForget = new LinkedList<ICFSecTSecGrpIncObj>();
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
			schema.getCFSecBackingStore().getTableTSecGrpInc().deleteTSecGrpIncByIncludeIdx( null,
				IncludeGroupId );
		}
		deepDisposeTSecGrpIncByIncludeIdx( IncludeGroupId );
	}

	@Override
	public void deleteTSecGrpIncByUIncludeIdx( CFLibDbKeyHash256 TenantId,
		CFLibDbKeyHash256 TSecGroupId,
		CFLibDbKeyHash256 IncludeGroupId )
	{
		if( indexByUIncludeIdx == null ) {
			indexByUIncludeIdx = new HashMap< ICFSecTSecGrpIncByUIncludeIdxKey,
				ICFSecTSecGrpIncObj >();
		}
		ICFSecTSecGrpIncByUIncludeIdxKey key = schema.getCFSecBackingStore().getFactoryTSecGrpInc().newByUIncludeIdxKey();
		key.setRequiredTenantId( TenantId );
		key.setRequiredTSecGroupId( TSecGroupId );
		key.setRequiredIncludeGroupId( IncludeGroupId );
		ICFSecTSecGrpIncObj obj = null;
		if( indexByUIncludeIdx.containsKey( key ) ) {
			obj = indexByUIncludeIdx.get( key );
			schema.getCFSecBackingStore().getTableTSecGrpInc().deleteTSecGrpIncByUIncludeIdx( null,
				TenantId,
				TSecGroupId,
				IncludeGroupId );
			obj.forget();
		}
		else {
			schema.getCFSecBackingStore().getTableTSecGrpInc().deleteTSecGrpIncByUIncludeIdx( null,
				TenantId,
				TSecGroupId,
				IncludeGroupId );
		}
		deepDisposeTSecGrpIncByUIncludeIdx( TenantId,
				TSecGroupId,
				IncludeGroupId );
	}
}