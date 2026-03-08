// Description: Java 25 Table Object implementation for SecSession.

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

public class CFIntSecSessionTableObj
	implements ICFIntSecSessionTableObj
{
	protected ICFIntSchemaObj schema;
	private Map<CFLibDbKeyHash256, ICFSecSecSessionObj> members;
	private Map<CFLibDbKeyHash256, ICFSecSecSessionObj> allSecSession;
	private Map< ICFSecSecSessionBySecUserIdxKey,
		Map<CFLibDbKeyHash256, ICFSecSecSessionObj > > indexBySecUserIdx;
	private Map< ICFSecSecSessionBySecDevIdxKey,
		Map<CFLibDbKeyHash256, ICFSecSecSessionObj > > indexBySecDevIdx;
	private Map< ICFSecSecSessionByStartIdxKey,
		ICFSecSecSessionObj > indexByStartIdx;
	private Map< ICFSecSecSessionByFinishIdxKey,
		Map<CFLibDbKeyHash256, ICFSecSecSessionObj > > indexByFinishIdx;
	private Map< ICFSecSecSessionBySecProxyIdxKey,
		Map<CFLibDbKeyHash256, ICFSecSecSessionObj > > indexBySecProxyIdx;
	public static String TABLE_NAME = "SecSession";
	public static String TABLE_DBNAME = "secsess";

	public CFIntSecSessionTableObj() {
		schema = null;
		members = new HashMap<CFLibDbKeyHash256, ICFSecSecSessionObj>();
		allSecSession = null;
		indexBySecUserIdx = null;
		indexBySecDevIdx = null;
		indexByStartIdx = null;
		indexByFinishIdx = null;
		indexBySecProxyIdx = null;
	}

	public CFIntSecSessionTableObj( ICFSecSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<CFLibDbKeyHash256, ICFSecSecSessionObj>();
		allSecSession = null;
		indexBySecUserIdx = null;
		indexBySecDevIdx = null;
		indexByStartIdx = null;
		indexByFinishIdx = null;
		indexBySecProxyIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFSecSecSessionTableObj.getRuntimeClassCode();
	}	

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	public static int getBackingClassCode() {
		return( CFSecSecSessionTableObj.getBackingClassCode() );
	}

	/**
	 *	Get the runtime class code for this table; this value is only stable after the application is fully initialized.
	 *
	 *	@return runtimeClassCode
	 */
	public static int getRuntimeClassCode() {
		return( CFSecSecSessionTableObj.getRuntimeClassCode() );
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
		allSecSession = null;
		indexBySecUserIdx = null;
		indexBySecDevIdx = null;
		indexByStartIdx = null;
		indexByFinishIdx = null;
		indexBySecProxyIdx = null;
		List<ICFSecSecSessionObj> toForget = new LinkedList<ICFSecSecSessionObj>();
		ICFSecSecSessionObj cur = null;
		Iterator<ICFSecSecSessionObj> iter = members.values().iterator();
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
	 *	CFIntSecSessionObj.
	 */
	@Override
	public ICFSecSecSessionObj newInstance() {
		ICFSecSecSessionObj inst = new CFIntSecSessionObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntSecSessionObj.
	 */
	@Override
	public ICFSecSecSessionEditObj newEditInstance( ICFSecSecSessionObj orig ) {
		ICFSecSecSessionEditObj edit = new CFIntSecSessionEditObj( orig );
		return( edit );
	}

	@Override
	public ICFSecSecSessionObj realiseSecSession( ICFSecSecSessionObj Obj ) {
		ICFSecSecSessionObj obj = Obj;
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecSecSessionObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFSecSecSessionObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexBySecUserIdx != null ) {
				ICFSecSecSessionBySecUserIdxKey keySecUserIdx =
					schema.getCFSecBackingStore().getFactorySecSession().newBySecUserIdxKey();
				keySecUserIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				Map<CFLibDbKeyHash256, ICFSecSecSessionObj > mapSecUserIdx = indexBySecUserIdx.get( keySecUserIdx );
				if( mapSecUserIdx != null ) {
					mapSecUserIdx.remove( keepObj.getPKey() );
					if( mapSecUserIdx.size() <= 0 ) {
						indexBySecUserIdx.remove( keySecUserIdx );
					}
				}
			}

			if( indexBySecDevIdx != null ) {
				ICFSecSecSessionBySecDevIdxKey keySecDevIdx =
					schema.getCFSecBackingStore().getFactorySecSession().newBySecDevIdxKey();
				keySecDevIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				keySecDevIdx.setOptionalSecDevName( keepObj.getOptionalSecDevName() );
				Map<CFLibDbKeyHash256, ICFSecSecSessionObj > mapSecDevIdx = indexBySecDevIdx.get( keySecDevIdx );
				if( mapSecDevIdx != null ) {
					mapSecDevIdx.remove( keepObj.getPKey() );
					if( mapSecDevIdx.size() <= 0 ) {
						indexBySecDevIdx.remove( keySecDevIdx );
					}
				}
			}

			if( indexByStartIdx != null ) {
				ICFSecSecSessionByStartIdxKey keyStartIdx =
					schema.getCFSecBackingStore().getFactorySecSession().newByStartIdxKey();
				keyStartIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				keyStartIdx.setRequiredStart( keepObj.getRequiredStart() );
				indexByStartIdx.remove( keyStartIdx );
			}

			if( indexByFinishIdx != null ) {
				ICFSecSecSessionByFinishIdxKey keyFinishIdx =
					schema.getCFSecBackingStore().getFactorySecSession().newByFinishIdxKey();
				keyFinishIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				keyFinishIdx.setOptionalFinish( keepObj.getOptionalFinish() );
				Map<CFLibDbKeyHash256, ICFSecSecSessionObj > mapFinishIdx = indexByFinishIdx.get( keyFinishIdx );
				if( mapFinishIdx != null ) {
					mapFinishIdx.remove( keepObj.getPKey() );
					if( mapFinishIdx.size() <= 0 ) {
						indexByFinishIdx.remove( keyFinishIdx );
					}
				}
			}

			if( indexBySecProxyIdx != null ) {
				ICFSecSecSessionBySecProxyIdxKey keySecProxyIdx =
					schema.getCFSecBackingStore().getFactorySecSession().newBySecProxyIdxKey();
				keySecProxyIdx.setOptionalSecProxyId( keepObj.getOptionalSecProxyId() );
				Map<CFLibDbKeyHash256, ICFSecSecSessionObj > mapSecProxyIdx = indexBySecProxyIdx.get( keySecProxyIdx );
				if( mapSecProxyIdx != null ) {
					mapSecProxyIdx.remove( keepObj.getPKey() );
					if( mapSecProxyIdx.size() <= 0 ) {
						indexBySecProxyIdx.remove( keySecProxyIdx );
					}
				}
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexBySecUserIdx != null ) {
				ICFSecSecSessionBySecUserIdxKey keySecUserIdx =
					schema.getCFSecBackingStore().getFactorySecSession().newBySecUserIdxKey();
				keySecUserIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				Map<CFLibDbKeyHash256, ICFSecSecSessionObj > mapSecUserIdx = indexBySecUserIdx.get( keySecUserIdx );
				if( mapSecUserIdx != null ) {
					mapSecUserIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexBySecDevIdx != null ) {
				ICFSecSecSessionBySecDevIdxKey keySecDevIdx =
					schema.getCFSecBackingStore().getFactorySecSession().newBySecDevIdxKey();
				keySecDevIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				keySecDevIdx.setOptionalSecDevName( keepObj.getOptionalSecDevName() );
				Map<CFLibDbKeyHash256, ICFSecSecSessionObj > mapSecDevIdx = indexBySecDevIdx.get( keySecDevIdx );
				if( mapSecDevIdx != null ) {
					mapSecDevIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByStartIdx != null ) {
				ICFSecSecSessionByStartIdxKey keyStartIdx =
					schema.getCFSecBackingStore().getFactorySecSession().newByStartIdxKey();
				keyStartIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				keyStartIdx.setRequiredStart( keepObj.getRequiredStart() );
				indexByStartIdx.put( keyStartIdx, keepObj );
			}

			if( indexByFinishIdx != null ) {
				ICFSecSecSessionByFinishIdxKey keyFinishIdx =
					schema.getCFSecBackingStore().getFactorySecSession().newByFinishIdxKey();
				keyFinishIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				keyFinishIdx.setOptionalFinish( keepObj.getOptionalFinish() );
				Map<CFLibDbKeyHash256, ICFSecSecSessionObj > mapFinishIdx = indexByFinishIdx.get( keyFinishIdx );
				if( mapFinishIdx != null ) {
					mapFinishIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexBySecProxyIdx != null ) {
				ICFSecSecSessionBySecProxyIdxKey keySecProxyIdx =
					schema.getCFSecBackingStore().getFactorySecSession().newBySecProxyIdxKey();
				keySecProxyIdx.setOptionalSecProxyId( keepObj.getOptionalSecProxyId() );
				Map<CFLibDbKeyHash256, ICFSecSecSessionObj > mapSecProxyIdx = indexBySecProxyIdx.get( keySecProxyIdx );
				if( mapSecProxyIdx != null ) {
					mapSecProxyIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( allSecSession != null ) {
				allSecSession.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allSecSession != null ) {
				allSecSession.put( keepObj.getPKey(), keepObj );
			}

			if( indexBySecUserIdx != null ) {
				ICFSecSecSessionBySecUserIdxKey keySecUserIdx =
					schema.getCFSecBackingStore().getFactorySecSession().newBySecUserIdxKey();
				keySecUserIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				Map<CFLibDbKeyHash256, ICFSecSecSessionObj > mapSecUserIdx = indexBySecUserIdx.get( keySecUserIdx );
				if( mapSecUserIdx != null ) {
					mapSecUserIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexBySecDevIdx != null ) {
				ICFSecSecSessionBySecDevIdxKey keySecDevIdx =
					schema.getCFSecBackingStore().getFactorySecSession().newBySecDevIdxKey();
				keySecDevIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				keySecDevIdx.setOptionalSecDevName( keepObj.getOptionalSecDevName() );
				Map<CFLibDbKeyHash256, ICFSecSecSessionObj > mapSecDevIdx = indexBySecDevIdx.get( keySecDevIdx );
				if( mapSecDevIdx != null ) {
					mapSecDevIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByStartIdx != null ) {
				ICFSecSecSessionByStartIdxKey keyStartIdx =
					schema.getCFSecBackingStore().getFactorySecSession().newByStartIdxKey();
				keyStartIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				keyStartIdx.setRequiredStart( keepObj.getRequiredStart() );
				indexByStartIdx.put( keyStartIdx, keepObj );
			}

			if( indexByFinishIdx != null ) {
				ICFSecSecSessionByFinishIdxKey keyFinishIdx =
					schema.getCFSecBackingStore().getFactorySecSession().newByFinishIdxKey();
				keyFinishIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				keyFinishIdx.setOptionalFinish( keepObj.getOptionalFinish() );
				Map<CFLibDbKeyHash256, ICFSecSecSessionObj > mapFinishIdx = indexByFinishIdx.get( keyFinishIdx );
				if( mapFinishIdx != null ) {
					mapFinishIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexBySecProxyIdx != null ) {
				ICFSecSecSessionBySecProxyIdxKey keySecProxyIdx =
					schema.getCFSecBackingStore().getFactorySecSession().newBySecProxyIdxKey();
				keySecProxyIdx.setOptionalSecProxyId( keepObj.getOptionalSecProxyId() );
				Map<CFLibDbKeyHash256, ICFSecSecSessionObj > mapSecProxyIdx = indexBySecProxyIdx.get( keySecProxyIdx );
				if( mapSecProxyIdx != null ) {
					mapSecProxyIdx.put( keepObj.getPKey(), keepObj );
				}
			}

		}
		return( keepObj );
	}

	@Override
	public ICFSecSecSessionObj createSecSession( ICFSecSecSessionObj Obj ) {
		ICFSecSecSessionObj obj = Obj;
		ICFSecSecSession rec = obj.getSecSessionRec();
		schema.getCFSecBackingStore().getTableSecSession().createSecSession(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFSecSecSessionObj readSecSession( CFLibDbKeyHash256 pkey ) {
		return( readSecSession( pkey, false ) );
	}

	@Override
	public ICFSecSecSessionObj readSecSession( CFLibDbKeyHash256 pkey, boolean forceRead ) {
		ICFSecSecSessionObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFSecSecSession readRec = schema.getCFSecBackingStore().getTableSecSession().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getSecSessionTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFSecSecSessionObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecSecSessionObj readCachedSecSession( CFLibDbKeyHash256 pkey ) {
		ICFSecSecSessionObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeSecSession( ICFSecSecSessionObj obj )
	{
		final String S_ProcName = "CFIntSecSessionTableObj.reallyDeepDisposeSecSession() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecSecSessionObj existing = readCachedSecSession( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFSecSecSessionBySecUserIdxKey keySecUserIdx = schema.getCFSecBackingStore().getFactorySecSession().newBySecUserIdxKey();
		keySecUserIdx.setRequiredSecUserId( existing.getRequiredSecUserId() );

		ICFSecSecSessionBySecDevIdxKey keySecDevIdx = schema.getCFSecBackingStore().getFactorySecSession().newBySecDevIdxKey();
		keySecDevIdx.setRequiredSecUserId( existing.getRequiredSecUserId() );
		keySecDevIdx.setOptionalSecDevName( existing.getOptionalSecDevName() );

		ICFSecSecSessionByStartIdxKey keyStartIdx = schema.getCFSecBackingStore().getFactorySecSession().newByStartIdxKey();
		keyStartIdx.setRequiredSecUserId( existing.getRequiredSecUserId() );
		keyStartIdx.setRequiredStart( existing.getRequiredStart() );

		ICFSecSecSessionByFinishIdxKey keyFinishIdx = schema.getCFSecBackingStore().getFactorySecSession().newByFinishIdxKey();
		keyFinishIdx.setRequiredSecUserId( existing.getRequiredSecUserId() );
		keyFinishIdx.setOptionalFinish( existing.getOptionalFinish() );

		ICFSecSecSessionBySecProxyIdxKey keySecProxyIdx = schema.getCFSecBackingStore().getFactorySecSession().newBySecProxyIdxKey();
		keySecProxyIdx.setOptionalSecProxyId( existing.getOptionalSecProxyId() );



		if( indexBySecUserIdx != null ) {
			if( indexBySecUserIdx.containsKey( keySecUserIdx ) ) {
				indexBySecUserIdx.get( keySecUserIdx ).remove( pkey );
				if( indexBySecUserIdx.get( keySecUserIdx ).size() <= 0 ) {
					indexBySecUserIdx.remove( keySecUserIdx );
				}
			}
		}

		if( indexBySecDevIdx != null ) {
			if( indexBySecDevIdx.containsKey( keySecDevIdx ) ) {
				indexBySecDevIdx.get( keySecDevIdx ).remove( pkey );
				if( indexBySecDevIdx.get( keySecDevIdx ).size() <= 0 ) {
					indexBySecDevIdx.remove( keySecDevIdx );
				}
			}
		}

		if( indexByStartIdx != null ) {
			indexByStartIdx.remove( keyStartIdx );
		}

		if( indexByFinishIdx != null ) {
			if( indexByFinishIdx.containsKey( keyFinishIdx ) ) {
				indexByFinishIdx.get( keyFinishIdx ).remove( pkey );
				if( indexByFinishIdx.get( keyFinishIdx ).size() <= 0 ) {
					indexByFinishIdx.remove( keyFinishIdx );
				}
			}
		}

		if( indexBySecProxyIdx != null ) {
			if( indexBySecProxyIdx.containsKey( keySecProxyIdx ) ) {
				indexBySecProxyIdx.get( keySecProxyIdx ).remove( pkey );
				if( indexBySecProxyIdx.get( keySecProxyIdx ).size() <= 0 ) {
					indexBySecProxyIdx.remove( keySecProxyIdx );
				}
			}
		}


	}
	@Override
	public void deepDisposeSecSession( CFLibDbKeyHash256 pkey ) {
		ICFSecSecSessionObj obj = readCachedSecSession( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecSecSessionObj lockSecSession( CFLibDbKeyHash256 pkey ) {
		ICFSecSecSessionObj locked = null;
		ICFSecSecSession lockRec = schema.getCFSecBackingStore().getTableSecSession().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getSecSessionTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFSecSecSessionObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockSecSession", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFSecSecSessionObj> readAllSecSession() {
		return( readAllSecSession( false ) );
	}

	@Override
	public List<ICFSecSecSessionObj> readAllSecSession( boolean forceRead ) {
		final String S_ProcName = "readAllSecSession";
		if( ( allSecSession == null ) || forceRead ) {
			Map<CFLibDbKeyHash256, ICFSecSecSessionObj> map = new HashMap<CFLibDbKeyHash256,ICFSecSecSessionObj>();
			allSecSession = map;
			ICFSecSecSession[] recList = schema.getCFSecBackingStore().getTableSecSession().readAllDerived( null );
			ICFSecSecSession rec;
			ICFSecSecSessionObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecSessionObj realised = (ICFSecSecSessionObj)obj.realise();
			}
		}
		int len = allSecSession.size();
		ICFSecSecSessionObj arr[] = new ICFSecSecSessionObj[len];
		Iterator<ICFSecSecSessionObj> valIter = allSecSession.values().iterator();
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
		ArrayList<ICFSecSecSessionObj> arrayList = new ArrayList<ICFSecSecSessionObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecSessionObj> cmp = new Comparator<ICFSecSecSessionObj>() {
			@Override
			public int compare( ICFSecSecSessionObj lhs, ICFSecSecSessionObj rhs ) {
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
		List<ICFSecSecSessionObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecSecSessionObj> readCachedAllSecSession() {
		final String S_ProcName = "readCachedAllSecSession";
		ArrayList<ICFSecSecSessionObj> arrayList = new ArrayList<ICFSecSecSessionObj>();
		if( allSecSession != null ) {
			int len = allSecSession.size();
			ICFSecSecSessionObj arr[] = new ICFSecSecSessionObj[len];
			Iterator<ICFSecSecSessionObj> valIter = allSecSession.values().iterator();
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
		Comparator<ICFSecSecSessionObj> cmp = new Comparator<ICFSecSecSessionObj>() {
			public int compare( ICFSecSecSessionObj lhs, ICFSecSecSessionObj rhs ) {
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
	 *	Return a sorted map of a page of the SecSession-derived instances in the database.
	 *
	 *	@return	List of ICFSecSecSessionObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	@Override
	public List<ICFSecSecSessionObj> pageAllSecSession(CFLibDbKeyHash256 priorSecSessionId )
	{
		final String S_ProcName = "pageAllSecSession";
		Map<CFLibDbKeyHash256, ICFSecSecSessionObj> map = new HashMap<CFLibDbKeyHash256,ICFSecSecSessionObj>();
		ICFSecSecSession[] recList = schema.getCFSecBackingStore().getTableSecSession().pageAllRec( null,
			priorSecSessionId );
		ICFSecSecSession rec;
		ICFSecSecSessionObj obj;
		ICFSecSecSessionObj realised;
		ArrayList<ICFSecSecSessionObj> arrayList = new ArrayList<ICFSecSecSessionObj>( recList.length );
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			realised = (ICFSecSecSessionObj)obj.realise();
			arrayList.add( realised );
		}
		return( arrayList );
	}

	@Override
	public ICFSecSecSessionObj readSecSessionByIdIdx( CFLibDbKeyHash256 SecSessionId )
	{
		return( readSecSessionByIdIdx( SecSessionId,
			false ) );
	}

	@Override
	public ICFSecSecSessionObj readSecSessionByIdIdx( CFLibDbKeyHash256 SecSessionId, boolean forceRead )
	{
		ICFSecSecSessionObj obj = readSecSession( SecSessionId, forceRead );
		return( obj );
	}

	@Override
	public List<ICFSecSecSessionObj> readSecSessionBySecUserIdx( CFLibDbKeyHash256 SecUserId )
	{
		return( readSecSessionBySecUserIdx( SecUserId,
			false ) );
	}

	@Override
	public List<ICFSecSecSessionObj> readSecSessionBySecUserIdx( CFLibDbKeyHash256 SecUserId,
		boolean forceRead )
	{
		final String S_ProcName = "readSecSessionBySecUserIdx";
		ICFSecSecSessionBySecUserIdxKey key = schema.getCFSecBackingStore().getFactorySecSession().newBySecUserIdxKey();
		key.setRequiredSecUserId( SecUserId );
		Map<CFLibDbKeyHash256, ICFSecSecSessionObj> dict;
		if( indexBySecUserIdx == null ) {
			indexBySecUserIdx = new HashMap< ICFSecSecSessionBySecUserIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecSessionObj > >();
		}
		if( ( ! forceRead ) && indexBySecUserIdx.containsKey( key ) ) {
			dict = indexBySecUserIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecSecSessionObj>();
			ICFSecSecSessionObj obj;
			ICFSecSecSession[] recList = schema.getCFSecBackingStore().getTableSecSession().readDerivedBySecUserIdx( null,
				SecUserId );
			ICFSecSecSession rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getSecSessionTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecSessionObj realised = (ICFSecSecSessionObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexBySecUserIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecSecSessionObj arr[] = new ICFSecSecSessionObj[len];
		Iterator<ICFSecSecSessionObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecSecSessionObj> arrayList = new ArrayList<ICFSecSecSessionObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecSessionObj> cmp = new Comparator<ICFSecSecSessionObj>() {
			@Override
			public int compare( ICFSecSecSessionObj lhs, ICFSecSecSessionObj rhs ) {
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
		List<ICFSecSecSessionObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecSecSessionObj> readSecSessionBySecDevIdx( CFLibDbKeyHash256 SecUserId,
		String SecDevName )
	{
		return( readSecSessionBySecDevIdx( SecUserId,
			SecDevName,
			false ) );
	}

	@Override
	public List<ICFSecSecSessionObj> readSecSessionBySecDevIdx( CFLibDbKeyHash256 SecUserId,
		String SecDevName,
		boolean forceRead )
	{
		final String S_ProcName = "readSecSessionBySecDevIdx";
		ICFSecSecSessionBySecDevIdxKey key = schema.getCFSecBackingStore().getFactorySecSession().newBySecDevIdxKey();
		key.setRequiredSecUserId( SecUserId );
		key.setOptionalSecDevName( SecDevName );
		Map<CFLibDbKeyHash256, ICFSecSecSessionObj> dict;
		if( indexBySecDevIdx == null ) {
			indexBySecDevIdx = new HashMap< ICFSecSecSessionBySecDevIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecSessionObj > >();
		}
		if( ( ! forceRead ) && indexBySecDevIdx.containsKey( key ) ) {
			dict = indexBySecDevIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecSecSessionObj>();
			ICFSecSecSessionObj obj;
			ICFSecSecSession[] recList = schema.getCFSecBackingStore().getTableSecSession().readDerivedBySecDevIdx( null,
				SecUserId,
				SecDevName );
			ICFSecSecSession rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getSecSessionTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecSessionObj realised = (ICFSecSecSessionObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexBySecDevIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecSecSessionObj arr[] = new ICFSecSecSessionObj[len];
		Iterator<ICFSecSecSessionObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecSecSessionObj> arrayList = new ArrayList<ICFSecSecSessionObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecSessionObj> cmp = new Comparator<ICFSecSecSessionObj>() {
			@Override
			public int compare( ICFSecSecSessionObj lhs, ICFSecSecSessionObj rhs ) {
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
		List<ICFSecSecSessionObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFSecSecSessionObj readSecSessionByStartIdx( CFLibDbKeyHash256 SecUserId,
		LocalDateTime Start )
	{
		return( readSecSessionByStartIdx( SecUserId,
			Start,
			false ) );
	}

	@Override
	public ICFSecSecSessionObj readSecSessionByStartIdx( CFLibDbKeyHash256 SecUserId,
		LocalDateTime Start, boolean forceRead )
	{
		if( indexByStartIdx == null ) {
			indexByStartIdx = new HashMap< ICFSecSecSessionByStartIdxKey,
				ICFSecSecSessionObj >();
		}
		ICFSecSecSessionByStartIdxKey key = schema.getCFSecBackingStore().getFactorySecSession().newByStartIdxKey();
		key.setRequiredSecUserId( SecUserId );
		key.setRequiredStart( Start );
		ICFSecSecSessionObj obj = null;
		if( ( ! forceRead ) && indexByStartIdx.containsKey( key ) ) {
			obj = indexByStartIdx.get( key );
		}
		else {
			ICFSecSecSession rec = schema.getCFSecBackingStore().getTableSecSession().readDerivedByStartIdx( null,
				SecUserId,
				Start );
			if( rec != null ) {
				obj = schema.getSecSessionTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFSecSecSessionObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public List<ICFSecSecSessionObj> readSecSessionByFinishIdx( CFLibDbKeyHash256 SecUserId,
		LocalDateTime Finish )
	{
		return( readSecSessionByFinishIdx( SecUserId,
			Finish,
			false ) );
	}

	@Override
	public List<ICFSecSecSessionObj> readSecSessionByFinishIdx( CFLibDbKeyHash256 SecUserId,
		LocalDateTime Finish,
		boolean forceRead )
	{
		final String S_ProcName = "readSecSessionByFinishIdx";
		ICFSecSecSessionByFinishIdxKey key = schema.getCFSecBackingStore().getFactorySecSession().newByFinishIdxKey();
		key.setRequiredSecUserId( SecUserId );
		key.setOptionalFinish( Finish );
		Map<CFLibDbKeyHash256, ICFSecSecSessionObj> dict;
		if( indexByFinishIdx == null ) {
			indexByFinishIdx = new HashMap< ICFSecSecSessionByFinishIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecSessionObj > >();
		}
		if( ( ! forceRead ) && indexByFinishIdx.containsKey( key ) ) {
			dict = indexByFinishIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecSecSessionObj>();
			ICFSecSecSessionObj obj;
			ICFSecSecSession[] recList = schema.getCFSecBackingStore().getTableSecSession().readDerivedByFinishIdx( null,
				SecUserId,
				Finish );
			ICFSecSecSession rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getSecSessionTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecSessionObj realised = (ICFSecSecSessionObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByFinishIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecSecSessionObj arr[] = new ICFSecSecSessionObj[len];
		Iterator<ICFSecSecSessionObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecSecSessionObj> arrayList = new ArrayList<ICFSecSecSessionObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecSessionObj> cmp = new Comparator<ICFSecSecSessionObj>() {
			@Override
			public int compare( ICFSecSecSessionObj lhs, ICFSecSecSessionObj rhs ) {
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
		List<ICFSecSecSessionObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecSecSessionObj> readSecSessionBySecProxyIdx( CFLibDbKeyHash256 SecProxyId )
	{
		return( readSecSessionBySecProxyIdx( SecProxyId,
			false ) );
	}

	@Override
	public List<ICFSecSecSessionObj> readSecSessionBySecProxyIdx( CFLibDbKeyHash256 SecProxyId,
		boolean forceRead )
	{
		final String S_ProcName = "readSecSessionBySecProxyIdx";
		ICFSecSecSessionBySecProxyIdxKey key = schema.getCFSecBackingStore().getFactorySecSession().newBySecProxyIdxKey();
		key.setOptionalSecProxyId( SecProxyId );
		Map<CFLibDbKeyHash256, ICFSecSecSessionObj> dict;
		if( indexBySecProxyIdx == null ) {
			indexBySecProxyIdx = new HashMap< ICFSecSecSessionBySecProxyIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecSessionObj > >();
		}
		if( ( ! forceRead ) && indexBySecProxyIdx.containsKey( key ) ) {
			dict = indexBySecProxyIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecSecSessionObj>();
			ICFSecSecSessionObj obj;
			ICFSecSecSession[] recList = schema.getCFSecBackingStore().getTableSecSession().readDerivedBySecProxyIdx( null,
				SecProxyId );
			ICFSecSecSession rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getSecSessionTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecSessionObj realised = (ICFSecSecSessionObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexBySecProxyIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecSecSessionObj arr[] = new ICFSecSecSessionObj[len];
		Iterator<ICFSecSecSessionObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecSecSessionObj> arrayList = new ArrayList<ICFSecSecSessionObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecSessionObj> cmp = new Comparator<ICFSecSecSessionObj>() {
			@Override
			public int compare( ICFSecSecSessionObj lhs, ICFSecSecSessionObj rhs ) {
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
		List<ICFSecSecSessionObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFSecSecSessionObj readCachedSecSessionByIdIdx( CFLibDbKeyHash256 SecSessionId )
	{
		ICFSecSecSessionObj obj = null;
		obj = readCachedSecSession( SecSessionId );
		return( obj );
	}

	@Override
	public List<ICFSecSecSessionObj> readCachedSecSessionBySecUserIdx( CFLibDbKeyHash256 SecUserId )
	{
		final String S_ProcName = "readCachedSecSessionBySecUserIdx";
		ICFSecSecSessionBySecUserIdxKey key = schema.getCFSecBackingStore().getFactorySecSession().newBySecUserIdxKey();
		key.setRequiredSecUserId( SecUserId );
		ArrayList<ICFSecSecSessionObj> arrayList = new ArrayList<ICFSecSecSessionObj>();
		if( indexBySecUserIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecSecSessionObj> dict;
			if( indexBySecUserIdx.containsKey( key ) ) {
				dict = indexBySecUserIdx.get( key );
				int len = dict.size();
				ICFSecSecSessionObj arr[] = new ICFSecSecSessionObj[len];
				Iterator<ICFSecSecSessionObj> valIter = dict.values().iterator();
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
			ICFSecSecSessionObj obj;
			Iterator<ICFSecSecSessionObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecSecSessionObj> cmp = new Comparator<ICFSecSecSessionObj>() {
			@Override
			public int compare( ICFSecSecSessionObj lhs, ICFSecSecSessionObj rhs ) {
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
	public List<ICFSecSecSessionObj> readCachedSecSessionBySecDevIdx( CFLibDbKeyHash256 SecUserId,
		String SecDevName )
	{
		final String S_ProcName = "readCachedSecSessionBySecDevIdx";
		ICFSecSecSessionBySecDevIdxKey key = schema.getCFSecBackingStore().getFactorySecSession().newBySecDevIdxKey();
		key.setRequiredSecUserId( SecUserId );
		key.setOptionalSecDevName( SecDevName );
		ArrayList<ICFSecSecSessionObj> arrayList = new ArrayList<ICFSecSecSessionObj>();
		if( indexBySecDevIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecSecSessionObj> dict;
			if( indexBySecDevIdx.containsKey( key ) ) {
				dict = indexBySecDevIdx.get( key );
				int len = dict.size();
				ICFSecSecSessionObj arr[] = new ICFSecSecSessionObj[len];
				Iterator<ICFSecSecSessionObj> valIter = dict.values().iterator();
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
			ICFSecSecSessionObj obj;
			Iterator<ICFSecSecSessionObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecSecSessionObj> cmp = new Comparator<ICFSecSecSessionObj>() {
			@Override
			public int compare( ICFSecSecSessionObj lhs, ICFSecSecSessionObj rhs ) {
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
	public ICFSecSecSessionObj readCachedSecSessionByStartIdx( CFLibDbKeyHash256 SecUserId,
		LocalDateTime Start )
	{
		ICFSecSecSessionObj obj = null;
		ICFSecSecSessionByStartIdxKey key = schema.getCFSecBackingStore().getFactorySecSession().newByStartIdxKey();
		key.setRequiredSecUserId( SecUserId );
		key.setRequiredStart( Start );
		if( indexByStartIdx != null ) {
			if( indexByStartIdx.containsKey( key ) ) {
				obj = indexByStartIdx.get( key );
			}
			else {
				Iterator<ICFSecSecSessionObj> valIter = members.values().iterator();
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
			Iterator<ICFSecSecSessionObj> valIter = members.values().iterator();
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
	public List<ICFSecSecSessionObj> readCachedSecSessionByFinishIdx( CFLibDbKeyHash256 SecUserId,
		LocalDateTime Finish )
	{
		final String S_ProcName = "readCachedSecSessionByFinishIdx";
		ICFSecSecSessionByFinishIdxKey key = schema.getCFSecBackingStore().getFactorySecSession().newByFinishIdxKey();
		key.setRequiredSecUserId( SecUserId );
		key.setOptionalFinish( Finish );
		ArrayList<ICFSecSecSessionObj> arrayList = new ArrayList<ICFSecSecSessionObj>();
		if( indexByFinishIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecSecSessionObj> dict;
			if( indexByFinishIdx.containsKey( key ) ) {
				dict = indexByFinishIdx.get( key );
				int len = dict.size();
				ICFSecSecSessionObj arr[] = new ICFSecSecSessionObj[len];
				Iterator<ICFSecSecSessionObj> valIter = dict.values().iterator();
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
			ICFSecSecSessionObj obj;
			Iterator<ICFSecSecSessionObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecSecSessionObj> cmp = new Comparator<ICFSecSecSessionObj>() {
			@Override
			public int compare( ICFSecSecSessionObj lhs, ICFSecSecSessionObj rhs ) {
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
	public List<ICFSecSecSessionObj> readCachedSecSessionBySecProxyIdx( CFLibDbKeyHash256 SecProxyId )
	{
		final String S_ProcName = "readCachedSecSessionBySecProxyIdx";
		ICFSecSecSessionBySecProxyIdxKey key = schema.getCFSecBackingStore().getFactorySecSession().newBySecProxyIdxKey();
		key.setOptionalSecProxyId( SecProxyId );
		ArrayList<ICFSecSecSessionObj> arrayList = new ArrayList<ICFSecSecSessionObj>();
		if( indexBySecProxyIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecSecSessionObj> dict;
			if( indexBySecProxyIdx.containsKey( key ) ) {
				dict = indexBySecProxyIdx.get( key );
				int len = dict.size();
				ICFSecSecSessionObj arr[] = new ICFSecSecSessionObj[len];
				Iterator<ICFSecSecSessionObj> valIter = dict.values().iterator();
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
			ICFSecSecSessionObj obj;
			Iterator<ICFSecSecSessionObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecSecSessionObj> cmp = new Comparator<ICFSecSecSessionObj>() {
			@Override
			public int compare( ICFSecSecSessionObj lhs, ICFSecSecSessionObj rhs ) {
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
	public void deepDisposeSecSessionByIdIdx( CFLibDbKeyHash256 SecSessionId )
	{
		ICFSecSecSessionObj obj = readCachedSecSessionByIdIdx( SecSessionId );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeSecSessionBySecUserIdx( CFLibDbKeyHash256 SecUserId )
	{
		final String S_ProcName = "deepDisposeSecSessionBySecUserIdx";
		ICFSecSecSessionObj obj;
		List<ICFSecSecSessionObj> arrayList = readCachedSecSessionBySecUserIdx( SecUserId );
		if( arrayList != null )  {
			Iterator<ICFSecSecSessionObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeSecSessionBySecDevIdx( CFLibDbKeyHash256 SecUserId,
		String SecDevName )
	{
		final String S_ProcName = "deepDisposeSecSessionBySecDevIdx";
		ICFSecSecSessionObj obj;
		List<ICFSecSecSessionObj> arrayList = readCachedSecSessionBySecDevIdx( SecUserId,
				SecDevName );
		if( arrayList != null )  {
			Iterator<ICFSecSecSessionObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeSecSessionByStartIdx( CFLibDbKeyHash256 SecUserId,
		LocalDateTime Start )
	{
		ICFSecSecSessionObj obj = readCachedSecSessionByStartIdx( SecUserId,
				Start );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeSecSessionByFinishIdx( CFLibDbKeyHash256 SecUserId,
		LocalDateTime Finish )
	{
		final String S_ProcName = "deepDisposeSecSessionByFinishIdx";
		ICFSecSecSessionObj obj;
		List<ICFSecSecSessionObj> arrayList = readCachedSecSessionByFinishIdx( SecUserId,
				Finish );
		if( arrayList != null )  {
			Iterator<ICFSecSecSessionObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeSecSessionBySecProxyIdx( CFLibDbKeyHash256 SecProxyId )
	{
		final String S_ProcName = "deepDisposeSecSessionBySecProxyIdx";
		ICFSecSecSessionObj obj;
		List<ICFSecSecSessionObj> arrayList = readCachedSecSessionBySecProxyIdx( SecProxyId );
		if( arrayList != null )  {
			Iterator<ICFSecSecSessionObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	/**
	 *	Read a page of data as a List of SecSession-derived instances sorted by their primary keys,
	 *	as identified by the duplicate SecUserIdx key attributes.
	 *
	 *	@param	SecUserId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return	A List of SecSession-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecSecSessionObj> pageSecSessionBySecUserIdx( CFLibDbKeyHash256 SecUserId,
		CFLibDbKeyHash256 priorSecSessionId )
	{
		final String S_ProcName = "pageSecSessionBySecUserIdx";
		ICFSecSecSessionBySecUserIdxKey key = schema.getCFSecBackingStore().getFactorySecSession().newBySecUserIdxKey();
		key.setRequiredSecUserId( SecUserId );
		List<ICFSecSecSessionObj> retList = new LinkedList<ICFSecSecSessionObj>();
		ICFSecSecSessionObj obj;
		ICFSecSecSession[] recList = schema.getCFSecBackingStore().getTableSecSession().pageRecBySecUserIdx( null,
				SecUserId,
			priorSecSessionId );
		ICFSecSecSession rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getSecSessionTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecSecSessionObj realised = (ICFSecSecSessionObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	/**
	 *	Read a page of data as a List of SecSession-derived instances sorted by their primary keys,
	 *	as identified by the duplicate SecDevIdx key attributes.
	 *
	 *	@param	SecUserId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@param	SecDevName	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return	A List of SecSession-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecSecSessionObj> pageSecSessionBySecDevIdx( CFLibDbKeyHash256 SecUserId,
		String SecDevName,
		CFLibDbKeyHash256 priorSecSessionId )
	{
		final String S_ProcName = "pageSecSessionBySecDevIdx";
		ICFSecSecSessionBySecDevIdxKey key = schema.getCFSecBackingStore().getFactorySecSession().newBySecDevIdxKey();
		key.setRequiredSecUserId( SecUserId );
		key.setOptionalSecDevName( SecDevName );
		List<ICFSecSecSessionObj> retList = new LinkedList<ICFSecSecSessionObj>();
		ICFSecSecSessionObj obj;
		ICFSecSecSession[] recList = schema.getCFSecBackingStore().getTableSecSession().pageRecBySecDevIdx( null,
				SecUserId,
				SecDevName,
			priorSecSessionId );
		ICFSecSecSession rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getSecSessionTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecSecSessionObj realised = (ICFSecSecSessionObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	/**
	 *	Read a page of data as a List of SecSession-derived instances sorted by their primary keys,
	 *	as identified by the duplicate FinishIdx key attributes.
	 *
	 *	@param	SecUserId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@param	Finish	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return	A List of SecSession-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecSecSessionObj> pageSecSessionByFinishIdx( CFLibDbKeyHash256 SecUserId,
		LocalDateTime Finish,
		CFLibDbKeyHash256 priorSecSessionId )
	{
		final String S_ProcName = "pageSecSessionByFinishIdx";
		ICFSecSecSessionByFinishIdxKey key = schema.getCFSecBackingStore().getFactorySecSession().newByFinishIdxKey();
		key.setRequiredSecUserId( SecUserId );
		key.setOptionalFinish( Finish );
		List<ICFSecSecSessionObj> retList = new LinkedList<ICFSecSecSessionObj>();
		ICFSecSecSessionObj obj;
		ICFSecSecSession[] recList = schema.getCFSecBackingStore().getTableSecSession().pageRecByFinishIdx( null,
				SecUserId,
				Finish,
			priorSecSessionId );
		ICFSecSecSession rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getSecSessionTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecSecSessionObj realised = (ICFSecSecSessionObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	/**
	 *	Read a page of data as a List of SecSession-derived instances sorted by their primary keys,
	 *	as identified by the duplicate SecProxyIdx key attributes.
	 *
	 *	@param	SecProxyId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return	A List of SecSession-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecSecSessionObj> pageSecSessionBySecProxyIdx( CFLibDbKeyHash256 SecProxyId,
		CFLibDbKeyHash256 priorSecSessionId )
	{
		final String S_ProcName = "pageSecSessionBySecProxyIdx";
		ICFSecSecSessionBySecProxyIdxKey key = schema.getCFSecBackingStore().getFactorySecSession().newBySecProxyIdxKey();
		key.setOptionalSecProxyId( SecProxyId );
		List<ICFSecSecSessionObj> retList = new LinkedList<ICFSecSecSessionObj>();
		ICFSecSecSessionObj obj;
		ICFSecSecSession[] recList = schema.getCFSecBackingStore().getTableSecSession().pageRecBySecProxyIdx( null,
				SecProxyId,
			priorSecSessionId );
		ICFSecSecSession rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getSecSessionTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecSecSessionObj realised = (ICFSecSecSessionObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	@Override
	public ICFSecSecSessionObj updateSecSession( ICFSecSecSessionObj Obj ) {
		ICFSecSecSessionObj obj = Obj;
		schema.getCFSecBackingStore().getTableSecSession().updateSecSession( null,
			Obj.getSecSessionRec() );
		obj = (ICFSecSecSessionObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteSecSession( ICFSecSecSessionObj Obj ) {
		ICFSecSecSessionObj obj = Obj;
		schema.getCFSecBackingStore().getTableSecSession().deleteSecSession( null,
			obj.getSecSessionRec() );
		Obj.forget();
	}

	@Override
	public void deleteSecSessionByIdIdx( CFLibDbKeyHash256 SecSessionId )
	{
		ICFSecSecSessionObj obj = readSecSession(SecSessionId);
		if( obj != null ) {
			ICFSecSecSessionEditObj editObj = (ICFSecSecSessionEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFSecSecSessionEditObj)obj.beginEdit();
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
		deepDisposeSecSessionByIdIdx( SecSessionId );
	}

	@Override
	public void deleteSecSessionBySecUserIdx( CFLibDbKeyHash256 SecUserId )
	{
		ICFSecSecSessionBySecUserIdxKey key = schema.getCFSecBackingStore().getFactorySecSession().newBySecUserIdxKey();
		key.setRequiredSecUserId( SecUserId );
		if( indexBySecUserIdx == null ) {
			indexBySecUserIdx = new HashMap< ICFSecSecSessionBySecUserIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecSessionObj > >();
		}
		if( indexBySecUserIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecSecSessionObj> dict = indexBySecUserIdx.get( key );
			schema.getCFSecBackingStore().getTableSecSession().deleteSecSessionBySecUserIdx( null,
				SecUserId );
			Iterator<ICFSecSecSessionObj> iter = dict.values().iterator();
			ICFSecSecSessionObj obj;
			List<ICFSecSecSessionObj> toForget = new LinkedList<ICFSecSecSessionObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexBySecUserIdx.remove( key );
		}
		else {
			schema.getCFSecBackingStore().getTableSecSession().deleteSecSessionBySecUserIdx( null,
				SecUserId );
		}
		deepDisposeSecSessionBySecUserIdx( SecUserId );
	}

	@Override
	public void deleteSecSessionBySecDevIdx( CFLibDbKeyHash256 SecUserId,
		String SecDevName )
	{
		ICFSecSecSessionBySecDevIdxKey key = schema.getCFSecBackingStore().getFactorySecSession().newBySecDevIdxKey();
		key.setRequiredSecUserId( SecUserId );
		key.setOptionalSecDevName( SecDevName );
		if( indexBySecDevIdx == null ) {
			indexBySecDevIdx = new HashMap< ICFSecSecSessionBySecDevIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecSessionObj > >();
		}
		if( indexBySecDevIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecSecSessionObj> dict = indexBySecDevIdx.get( key );
			schema.getCFSecBackingStore().getTableSecSession().deleteSecSessionBySecDevIdx( null,
				SecUserId,
				SecDevName );
			Iterator<ICFSecSecSessionObj> iter = dict.values().iterator();
			ICFSecSecSessionObj obj;
			List<ICFSecSecSessionObj> toForget = new LinkedList<ICFSecSecSessionObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexBySecDevIdx.remove( key );
		}
		else {
			schema.getCFSecBackingStore().getTableSecSession().deleteSecSessionBySecDevIdx( null,
				SecUserId,
				SecDevName );
		}
		deepDisposeSecSessionBySecDevIdx( SecUserId,
				SecDevName );
	}

	@Override
	public void deleteSecSessionByStartIdx( CFLibDbKeyHash256 SecUserId,
		LocalDateTime Start )
	{
		if( indexByStartIdx == null ) {
			indexByStartIdx = new HashMap< ICFSecSecSessionByStartIdxKey,
				ICFSecSecSessionObj >();
		}
		ICFSecSecSessionByStartIdxKey key = schema.getCFSecBackingStore().getFactorySecSession().newByStartIdxKey();
		key.setRequiredSecUserId( SecUserId );
		key.setRequiredStart( Start );
		ICFSecSecSessionObj obj = null;
		if( indexByStartIdx.containsKey( key ) ) {
			obj = indexByStartIdx.get( key );
			schema.getCFSecBackingStore().getTableSecSession().deleteSecSessionByStartIdx( null,
				SecUserId,
				Start );
			obj.forget();
		}
		else {
			schema.getCFSecBackingStore().getTableSecSession().deleteSecSessionByStartIdx( null,
				SecUserId,
				Start );
		}
		deepDisposeSecSessionByStartIdx( SecUserId,
				Start );
	}

	@Override
	public void deleteSecSessionByFinishIdx( CFLibDbKeyHash256 SecUserId,
		LocalDateTime Finish )
	{
		ICFSecSecSessionByFinishIdxKey key = schema.getCFSecBackingStore().getFactorySecSession().newByFinishIdxKey();
		key.setRequiredSecUserId( SecUserId );
		key.setOptionalFinish( Finish );
		if( indexByFinishIdx == null ) {
			indexByFinishIdx = new HashMap< ICFSecSecSessionByFinishIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecSessionObj > >();
		}
		if( indexByFinishIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecSecSessionObj> dict = indexByFinishIdx.get( key );
			schema.getCFSecBackingStore().getTableSecSession().deleteSecSessionByFinishIdx( null,
				SecUserId,
				Finish );
			Iterator<ICFSecSecSessionObj> iter = dict.values().iterator();
			ICFSecSecSessionObj obj;
			List<ICFSecSecSessionObj> toForget = new LinkedList<ICFSecSecSessionObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByFinishIdx.remove( key );
		}
		else {
			schema.getCFSecBackingStore().getTableSecSession().deleteSecSessionByFinishIdx( null,
				SecUserId,
				Finish );
		}
		deepDisposeSecSessionByFinishIdx( SecUserId,
				Finish );
	}

	@Override
	public void deleteSecSessionBySecProxyIdx( CFLibDbKeyHash256 SecProxyId )
	{
		ICFSecSecSessionBySecProxyIdxKey key = schema.getCFSecBackingStore().getFactorySecSession().newBySecProxyIdxKey();
		key.setOptionalSecProxyId( SecProxyId );
		if( indexBySecProxyIdx == null ) {
			indexBySecProxyIdx = new HashMap< ICFSecSecSessionBySecProxyIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecSessionObj > >();
		}
		if( indexBySecProxyIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecSecSessionObj> dict = indexBySecProxyIdx.get( key );
			schema.getCFSecBackingStore().getTableSecSession().deleteSecSessionBySecProxyIdx( null,
				SecProxyId );
			Iterator<ICFSecSecSessionObj> iter = dict.values().iterator();
			ICFSecSecSessionObj obj;
			List<ICFSecSecSessionObj> toForget = new LinkedList<ICFSecSecSessionObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexBySecProxyIdx.remove( key );
		}
		else {
			schema.getCFSecBackingStore().getTableSecSession().deleteSecSessionBySecProxyIdx( null,
				SecProxyId );
		}
		deepDisposeSecSessionBySecProxyIdx( SecProxyId );
	}

	public ICFSecSecSessionObj getSystemSession() {
		ICFSecSecUserObj userObj;
		ICFSecSecSessionObj sessionObj;
		userObj = schema.getSecUserTableObj().getSystemUser();
		sessionObj = readSecSessionByStartIdx( userObj.getRequiredSecUserId(),
			LocalDateTime.now() );
		if( sessionObj == null ) {
			sessionObj = newInstance();
			ICFSecSecSessionEditObj sessionEdit = sessionObj.beginEdit();
			sessionEdit.setRequiredSecUserId( userObj.getPKey() );
			sessionEdit.setRequiredStart( LocalDateTime.now() );
			sessionEdit.setOptionalFinish( null );
			sessionObj = sessionEdit.create();
			sessionEdit = null;
		}
		return( sessionObj );
	}
}