// Description: Java 25 Table Object implementation for URLProtocol.

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

public class CFIntURLProtocolTableObj
	implements ICFIntURLProtocolTableObj
{
	protected ICFIntSchemaObj schema;
	protected static int runtimeClassCode = ICFIntURLProtocol.CLASS_CODE;
	protected static final int backingClassCode = ICFIntURLProtocol.CLASS_CODE;
	private Map<Integer, ICFIntURLProtocolObj> members;
	private Map<Integer, ICFIntURLProtocolObj> allURLProtocol;
	private Map< ICFIntURLProtocolByUNameIdxKey,
		ICFIntURLProtocolObj > indexByUNameIdx;
	private Map< ICFIntURLProtocolByIsSecureIdxKey,
		Map<Integer, ICFIntURLProtocolObj > > indexByIsSecureIdx;
	public static String TABLE_NAME = "URLProtocol";
	public static String TABLE_DBNAME = "urlproto";

	public CFIntURLProtocolTableObj() {
		schema = null;
		members = new HashMap<Integer, ICFIntURLProtocolObj>();
		allURLProtocol = null;
		indexByUNameIdx = null;
		indexByIsSecureIdx = null;
	}

	public CFIntURLProtocolTableObj( ICFIntSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<Integer, ICFIntURLProtocolObj>();
		allURLProtocol = null;
		indexByUNameIdx = null;
		indexByIsSecureIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFIntURLProtocolTableObj.getRuntimeClassCode();
	}	

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	public static int getBackingClassCode() {
		return( backingClassCode );
	}

	/**
	 *	Get the runtime class code for this table; this value is only stable after the application is fully initialized.
	 *
	 *	@return runtimeClassCode
	 */
	public static int getRuntimeClassCode() {
		return( runtimeClassCode );
	}

	/**
	 *	Set the runtime class code for this table; this is done only during application initialization by the SchemaObj's <tt>initClassCodes()</tt> static method,
	 *	which will only set the class codes once and never again.  Once set, the class codes are immutable within the application.
	 *	Application programmers should never invoke this method, so it has package access only.
	 *
	 *	@param	argNewClassCode	The runtime class code to be used by clients and integrated application logic to identify this table of this schema.
	 */
	static void setRuntimeClassCode(int argNewClassCode ) {
		if (argNewClassCode <= 0) {
			throw new CFLibArgumentUnderflowException(CFIntURLProtocolTableObj.class, "setRuntimeClassCode", 1, "argNewClassCode", argNewClassCode, 1);
		}
		runtimeClassCode = argNewClassCode;
	}

	@Override
	public ICFIntSchemaObj getSchema() {
		return( schema );
	}

	@Override
	public void setSchema( ICFIntSchemaObj value ) {
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
		allURLProtocol = null;
		indexByUNameIdx = null;
		indexByIsSecureIdx = null;
		List<ICFIntURLProtocolObj> toForget = new LinkedList<ICFIntURLProtocolObj>();
		ICFIntURLProtocolObj cur = null;
		Iterator<ICFIntURLProtocolObj> iter = members.values().iterator();
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
	 *	CFIntURLProtocolObj.
	 */
	@Override
	public ICFIntURLProtocolObj newInstance() {
		ICFIntURLProtocolObj inst = new CFIntURLProtocolObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntURLProtocolObj.
	 */
	@Override
	public ICFIntURLProtocolEditObj newEditInstance( ICFIntURLProtocolObj orig ) {
		ICFIntURLProtocolEditObj edit = new CFIntURLProtocolEditObj( orig );
		return( edit );
	}

	@Override
	public ICFIntURLProtocolObj realiseURLProtocol( ICFIntURLProtocolObj Obj ) {
		ICFIntURLProtocolObj obj = Obj;
		Integer pkey = obj.getPKey();
		ICFIntURLProtocolObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFIntURLProtocolObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByUNameIdx != null ) {
				ICFIntURLProtocolByUNameIdxKey keyUNameIdx =
					schema.getCFIntBackingStore().getFactoryURLProtocol().newByUNameIdxKey();
				keyUNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByUNameIdx.remove( keyUNameIdx );
			}

			if( indexByIsSecureIdx != null ) {
				ICFIntURLProtocolByIsSecureIdxKey keyIsSecureIdx =
					schema.getCFIntBackingStore().getFactoryURLProtocol().newByIsSecureIdxKey();
				keyIsSecureIdx.setRequiredIsSecure( keepObj.getRequiredIsSecure() );
				Map<Integer, ICFIntURLProtocolObj > mapIsSecureIdx = indexByIsSecureIdx.get( keyIsSecureIdx );
				if( mapIsSecureIdx != null ) {
					mapIsSecureIdx.remove( keepObj.getPKey() );
					if( mapIsSecureIdx.size() <= 0 ) {
						indexByIsSecureIdx.remove( keyIsSecureIdx );
					}
				}
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByUNameIdx != null ) {
				ICFIntURLProtocolByUNameIdxKey keyUNameIdx =
					schema.getCFIntBackingStore().getFactoryURLProtocol().newByUNameIdxKey();
				keyUNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByUNameIdx.put( keyUNameIdx, keepObj );
			}

			if( indexByIsSecureIdx != null ) {
				ICFIntURLProtocolByIsSecureIdxKey keyIsSecureIdx =
					schema.getCFIntBackingStore().getFactoryURLProtocol().newByIsSecureIdxKey();
				keyIsSecureIdx.setRequiredIsSecure( keepObj.getRequiredIsSecure() );
				Map<Integer, ICFIntURLProtocolObj > mapIsSecureIdx = indexByIsSecureIdx.get( keyIsSecureIdx );
				if( mapIsSecureIdx != null ) {
					mapIsSecureIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( allURLProtocol != null ) {
				allURLProtocol.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allURLProtocol != null ) {
				allURLProtocol.put( keepObj.getPKey(), keepObj );
			}

			if( indexByUNameIdx != null ) {
				ICFIntURLProtocolByUNameIdxKey keyUNameIdx =
					schema.getCFIntBackingStore().getFactoryURLProtocol().newByUNameIdxKey();
				keyUNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByUNameIdx.put( keyUNameIdx, keepObj );
			}

			if( indexByIsSecureIdx != null ) {
				ICFIntURLProtocolByIsSecureIdxKey keyIsSecureIdx =
					schema.getCFIntBackingStore().getFactoryURLProtocol().newByIsSecureIdxKey();
				keyIsSecureIdx.setRequiredIsSecure( keepObj.getRequiredIsSecure() );
				Map<Integer, ICFIntURLProtocolObj > mapIsSecureIdx = indexByIsSecureIdx.get( keyIsSecureIdx );
				if( mapIsSecureIdx != null ) {
					mapIsSecureIdx.put( keepObj.getPKey(), keepObj );
				}
			}

		}
		return( keepObj );
	}

	@Override
	public ICFIntURLProtocolObj createURLProtocol( ICFIntURLProtocolObj Obj ) {
		ICFIntURLProtocolObj obj = Obj;
		ICFIntURLProtocol rec = obj.getURLProtocolRec();
		schema.getCFIntBackingStore().getTableURLProtocol().createURLProtocol(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFIntURLProtocolObj readURLProtocol( Integer pkey ) {
		return( readURLProtocol( pkey, false ) );
	}

	@Override
	public ICFIntURLProtocolObj readURLProtocol( Integer pkey, boolean forceRead ) {
		ICFIntURLProtocolObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFIntURLProtocol readRec = schema.getCFIntBackingStore().getTableURLProtocol().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getURLProtocolTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFIntURLProtocolObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFIntURLProtocolObj readCachedURLProtocol( Integer pkey ) {
		ICFIntURLProtocolObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeURLProtocol( ICFIntURLProtocolObj obj )
	{
		final String S_ProcName = "CFIntURLProtocolTableObj.reallyDeepDisposeURLProtocol() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		Integer pkey = obj.getPKey();
		ICFIntURLProtocolObj existing = readCachedURLProtocol( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFIntURLProtocolByUNameIdxKey keyUNameIdx = schema.getCFIntBackingStore().getFactoryURLProtocol().newByUNameIdxKey();
		keyUNameIdx.setRequiredName( existing.getRequiredName() );

		ICFIntURLProtocolByIsSecureIdxKey keyIsSecureIdx = schema.getCFIntBackingStore().getFactoryURLProtocol().newByIsSecureIdxKey();
		keyIsSecureIdx.setRequiredIsSecure( existing.getRequiredIsSecure() );



		if( indexByUNameIdx != null ) {
			indexByUNameIdx.remove( keyUNameIdx );
		}

		if( indexByIsSecureIdx != null ) {
			if( indexByIsSecureIdx.containsKey( keyIsSecureIdx ) ) {
				indexByIsSecureIdx.get( keyIsSecureIdx ).remove( pkey );
				if( indexByIsSecureIdx.get( keyIsSecureIdx ).size() <= 0 ) {
					indexByIsSecureIdx.remove( keyIsSecureIdx );
				}
			}
		}


	}
	@Override
	public void deepDisposeURLProtocol( Integer pkey ) {
		ICFIntURLProtocolObj obj = readCachedURLProtocol( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFIntURLProtocolObj lockURLProtocol( Integer pkey ) {
		ICFIntURLProtocolObj locked = null;
		ICFIntURLProtocol lockRec = schema.getCFIntBackingStore().getTableURLProtocol().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getURLProtocolTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFIntURLProtocolObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockURLProtocol", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFIntURLProtocolObj> readAllURLProtocol() {
		return( readAllURLProtocol( false ) );
	}

	@Override
	public List<ICFIntURLProtocolObj> readAllURLProtocol( boolean forceRead ) {
		final String S_ProcName = "readAllURLProtocol";
		if( ( allURLProtocol == null ) || forceRead ) {
			Map<Integer, ICFIntURLProtocolObj> map = new HashMap<Integer,ICFIntURLProtocolObj>();
			allURLProtocol = map;
			ICFIntURLProtocol[] recList = schema.getCFIntBackingStore().getTableURLProtocol().readAllDerived( null );
			ICFIntURLProtocol rec;
			ICFIntURLProtocolObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntURLProtocolObj realised = (ICFIntURLProtocolObj)obj.realise();
			}
		}
		int len = allURLProtocol.size();
		ICFIntURLProtocolObj arr[] = new ICFIntURLProtocolObj[len];
		Iterator<ICFIntURLProtocolObj> valIter = allURLProtocol.values().iterator();
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
		ArrayList<ICFIntURLProtocolObj> arrayList = new ArrayList<ICFIntURLProtocolObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntURLProtocolObj> cmp = new Comparator<ICFIntURLProtocolObj>() {
			@Override
			public int compare( ICFIntURLProtocolObj lhs, ICFIntURLProtocolObj rhs ) {
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
					Integer lhsPKey = lhs.getPKey();
					Integer rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		List<ICFIntURLProtocolObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFIntURLProtocolObj> readCachedAllURLProtocol() {
		final String S_ProcName = "readCachedAllURLProtocol";
		ArrayList<ICFIntURLProtocolObj> arrayList = new ArrayList<ICFIntURLProtocolObj>();
		if( allURLProtocol != null ) {
			int len = allURLProtocol.size();
			ICFIntURLProtocolObj arr[] = new ICFIntURLProtocolObj[len];
			Iterator<ICFIntURLProtocolObj> valIter = allURLProtocol.values().iterator();
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
		Comparator<ICFIntURLProtocolObj> cmp = new Comparator<ICFIntURLProtocolObj>() {
			public int compare( ICFIntURLProtocolObj lhs, ICFIntURLProtocolObj rhs ) {
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
					Integer lhsPKey = lhs.getPKey();
					Integer rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		return( arrayList );
	}

	@Override
	public ICFIntURLProtocolObj readURLProtocolByIdIdx( int URLProtocolId )
	{
		return( readURLProtocolByIdIdx( URLProtocolId,
			false ) );
	}

	@Override
	public ICFIntURLProtocolObj readURLProtocolByIdIdx( int URLProtocolId, boolean forceRead )
	{
		ICFIntURLProtocolObj obj = readURLProtocol( URLProtocolId, forceRead );
		return( obj );
	}

	@Override
	public ICFIntURLProtocolObj readURLProtocolByUNameIdx( String Name )
	{
		return( readURLProtocolByUNameIdx( Name,
			false ) );
	}

	@Override
	public ICFIntURLProtocolObj readURLProtocolByUNameIdx( String Name, boolean forceRead )
	{
		if( indexByUNameIdx == null ) {
			indexByUNameIdx = new HashMap< ICFIntURLProtocolByUNameIdxKey,
				ICFIntURLProtocolObj >();
		}
		ICFIntURLProtocolByUNameIdxKey key = schema.getCFIntBackingStore().getFactoryURLProtocol().newByUNameIdxKey();
		key.setRequiredName( Name );
		ICFIntURLProtocolObj obj = null;
		if( ( ! forceRead ) && indexByUNameIdx.containsKey( key ) ) {
			obj = indexByUNameIdx.get( key );
		}
		else {
			ICFIntURLProtocol rec = schema.getCFIntBackingStore().getTableURLProtocol().readDerivedByUNameIdx( null,
				Name );
			if( rec != null ) {
				obj = schema.getURLProtocolTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFIntURLProtocolObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public List<ICFIntURLProtocolObj> readURLProtocolByIsSecureIdx( boolean IsSecure )
	{
		return( readURLProtocolByIsSecureIdx( IsSecure,
			false ) );
	}

	@Override
	public List<ICFIntURLProtocolObj> readURLProtocolByIsSecureIdx( boolean IsSecure,
		boolean forceRead )
	{
		final String S_ProcName = "readURLProtocolByIsSecureIdx";
		ICFIntURLProtocolByIsSecureIdxKey key = schema.getCFIntBackingStore().getFactoryURLProtocol().newByIsSecureIdxKey();
		key.setRequiredIsSecure( IsSecure );
		Map<Integer, ICFIntURLProtocolObj> dict;
		if( indexByIsSecureIdx == null ) {
			indexByIsSecureIdx = new HashMap< ICFIntURLProtocolByIsSecureIdxKey,
				Map< Integer, ICFIntURLProtocolObj > >();
		}
		if( ( ! forceRead ) && indexByIsSecureIdx.containsKey( key ) ) {
			dict = indexByIsSecureIdx.get( key );
		}
		else {
			dict = new HashMap<Integer, ICFIntURLProtocolObj>();
			ICFIntURLProtocolObj obj;
			ICFIntURLProtocol[] recList = schema.getCFIntBackingStore().getTableURLProtocol().readDerivedByIsSecureIdx( null,
				IsSecure );
			ICFIntURLProtocol rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getURLProtocolTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntURLProtocolObj realised = (ICFIntURLProtocolObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByIsSecureIdx.put( key, dict );
		}
		int len = dict.size();
		ICFIntURLProtocolObj arr[] = new ICFIntURLProtocolObj[len];
		Iterator<ICFIntURLProtocolObj> valIter = dict.values().iterator();
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
		ArrayList<ICFIntURLProtocolObj> arrayList = new ArrayList<ICFIntURLProtocolObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntURLProtocolObj> cmp = new Comparator<ICFIntURLProtocolObj>() {
			@Override
			public int compare( ICFIntURLProtocolObj lhs, ICFIntURLProtocolObj rhs ) {
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
					Integer lhsPKey = lhs.getPKey();
					Integer rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		List<ICFIntURLProtocolObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFIntURLProtocolObj readCachedURLProtocolByIdIdx( int URLProtocolId )
	{
		ICFIntURLProtocolObj obj = null;
		obj = readCachedURLProtocol( URLProtocolId );
		return( obj );
	}

	@Override
	public ICFIntURLProtocolObj readCachedURLProtocolByUNameIdx( String Name )
	{
		ICFIntURLProtocolObj obj = null;
		ICFIntURLProtocolByUNameIdxKey key = schema.getCFIntBackingStore().getFactoryURLProtocol().newByUNameIdxKey();
		key.setRequiredName( Name );
		if( indexByUNameIdx != null ) {
			if( indexByUNameIdx.containsKey( key ) ) {
				obj = indexByUNameIdx.get( key );
			}
			else {
				Iterator<ICFIntURLProtocolObj> valIter = members.values().iterator();
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
			Iterator<ICFIntURLProtocolObj> valIter = members.values().iterator();
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
	public List<ICFIntURLProtocolObj> readCachedURLProtocolByIsSecureIdx( boolean IsSecure )
	{
		final String S_ProcName = "readCachedURLProtocolByIsSecureIdx";
		ICFIntURLProtocolByIsSecureIdxKey key = schema.getCFIntBackingStore().getFactoryURLProtocol().newByIsSecureIdxKey();
		key.setRequiredIsSecure( IsSecure );
		ArrayList<ICFIntURLProtocolObj> arrayList = new ArrayList<ICFIntURLProtocolObj>();
		if( indexByIsSecureIdx != null ) {
			Map<Integer, ICFIntURLProtocolObj> dict;
			if( indexByIsSecureIdx.containsKey( key ) ) {
				dict = indexByIsSecureIdx.get( key );
				int len = dict.size();
				ICFIntURLProtocolObj arr[] = new ICFIntURLProtocolObj[len];
				Iterator<ICFIntURLProtocolObj> valIter = dict.values().iterator();
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
			ICFIntURLProtocolObj obj;
			Iterator<ICFIntURLProtocolObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFIntURLProtocolObj> cmp = new Comparator<ICFIntURLProtocolObj>() {
			@Override
			public int compare( ICFIntURLProtocolObj lhs, ICFIntURLProtocolObj rhs ) {
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
					Integer lhsPKey = lhs.getPKey();
					Integer rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		return( arrayList );
	}

	@Override
	public void deepDisposeURLProtocolByIdIdx( int URLProtocolId )
	{
		ICFIntURLProtocolObj obj = readCachedURLProtocolByIdIdx( URLProtocolId );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeURLProtocolByUNameIdx( String Name )
	{
		ICFIntURLProtocolObj obj = readCachedURLProtocolByUNameIdx( Name );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeURLProtocolByIsSecureIdx( boolean IsSecure )
	{
		final String S_ProcName = "deepDisposeURLProtocolByIsSecureIdx";
		ICFIntURLProtocolObj obj;
		List<ICFIntURLProtocolObj> arrayList = readCachedURLProtocolByIsSecureIdx( IsSecure );
		if( arrayList != null )  {
			Iterator<ICFIntURLProtocolObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public ICFIntURLProtocolObj updateURLProtocol( ICFIntURLProtocolObj Obj ) {
		ICFIntURLProtocolObj obj = Obj;
		schema.getCFIntBackingStore().getTableURLProtocol().updateURLProtocol( null,
			Obj.getURLProtocolRec() );
		obj = (ICFIntURLProtocolObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteURLProtocol( ICFIntURLProtocolObj Obj ) {
		ICFIntURLProtocolObj obj = Obj;
		schema.getCFIntBackingStore().getTableURLProtocol().deleteURLProtocol( null,
			obj.getURLProtocolRec() );
		Obj.forget();
	}

	@Override
	public void deleteURLProtocolByIdIdx( int URLProtocolId )
	{
		ICFIntURLProtocolObj obj = readURLProtocol(URLProtocolId);
		if( obj != null ) {
			ICFIntURLProtocolEditObj editObj = (ICFIntURLProtocolEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFIntURLProtocolEditObj)obj.beginEdit();
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
		deepDisposeURLProtocolByIdIdx( URLProtocolId );
	}

	@Override
	public void deleteURLProtocolByUNameIdx( String Name )
	{
		if( indexByUNameIdx == null ) {
			indexByUNameIdx = new HashMap< ICFIntURLProtocolByUNameIdxKey,
				ICFIntURLProtocolObj >();
		}
		ICFIntURLProtocolByUNameIdxKey key = schema.getCFIntBackingStore().getFactoryURLProtocol().newByUNameIdxKey();
		key.setRequiredName( Name );
		ICFIntURLProtocolObj obj = null;
		if( indexByUNameIdx.containsKey( key ) ) {
			obj = indexByUNameIdx.get( key );
			schema.getCFIntBackingStore().getTableURLProtocol().deleteURLProtocolByUNameIdx( null,
				Name );
			obj.forget();
		}
		else {
			schema.getCFIntBackingStore().getTableURLProtocol().deleteURLProtocolByUNameIdx( null,
				Name );
		}
		deepDisposeURLProtocolByUNameIdx( Name );
	}

	@Override
	public void deleteURLProtocolByIsSecureIdx( boolean IsSecure )
	{
		ICFIntURLProtocolByIsSecureIdxKey key = schema.getCFIntBackingStore().getFactoryURLProtocol().newByIsSecureIdxKey();
		key.setRequiredIsSecure( IsSecure );
		if( indexByIsSecureIdx == null ) {
			indexByIsSecureIdx = new HashMap< ICFIntURLProtocolByIsSecureIdxKey,
				Map< Integer, ICFIntURLProtocolObj > >();
		}
		if( indexByIsSecureIdx.containsKey( key ) ) {
			Map<Integer, ICFIntURLProtocolObj> dict = indexByIsSecureIdx.get( key );
			schema.getCFIntBackingStore().getTableURLProtocol().deleteURLProtocolByIsSecureIdx( null,
				IsSecure );
			Iterator<ICFIntURLProtocolObj> iter = dict.values().iterator();
			ICFIntURLProtocolObj obj;
			List<ICFIntURLProtocolObj> toForget = new LinkedList<ICFIntURLProtocolObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByIsSecureIdx.remove( key );
		}
		else {
			schema.getCFIntBackingStore().getTableURLProtocol().deleteURLProtocolByIsSecureIdx( null,
				IsSecure );
		}
		deepDisposeURLProtocolByIsSecureIdx( IsSecure );
	}
}