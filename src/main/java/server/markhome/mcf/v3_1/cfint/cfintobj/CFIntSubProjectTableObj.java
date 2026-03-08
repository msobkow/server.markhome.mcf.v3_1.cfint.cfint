// Description: Java 25 Table Object implementation for SubProject.

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

public class CFIntSubProjectTableObj
	implements ICFIntSubProjectTableObj
{
	protected ICFIntSchemaObj schema;
	protected static int runtimeClassCode = ICFIntSubProject.CLASS_CODE;
	protected static final int backingClassCode = ICFIntSubProject.CLASS_CODE;
	private Map<CFLibDbKeyHash256, ICFIntSubProjectObj> members;
	private Map<CFLibDbKeyHash256, ICFIntSubProjectObj> allSubProject;
	private Map< ICFIntSubProjectByTenantIdxKey,
		Map<CFLibDbKeyHash256, ICFIntSubProjectObj > > indexByTenantIdx;
	private Map< ICFIntSubProjectByTopProjectIdxKey,
		Map<CFLibDbKeyHash256, ICFIntSubProjectObj > > indexByTopProjectIdx;
	private Map< ICFIntSubProjectByNameIdxKey,
		ICFIntSubProjectObj > indexByNameIdx;
	public static String TABLE_NAME = "SubProject";
	public static String TABLE_DBNAME = "sprjdef";

	public CFIntSubProjectTableObj() {
		schema = null;
		members = new HashMap<CFLibDbKeyHash256, ICFIntSubProjectObj>();
		allSubProject = null;
		indexByTenantIdx = null;
		indexByTopProjectIdx = null;
		indexByNameIdx = null;
	}

	public CFIntSubProjectTableObj( ICFIntSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<CFLibDbKeyHash256, ICFIntSubProjectObj>();
		allSubProject = null;
		indexByTenantIdx = null;
		indexByTopProjectIdx = null;
		indexByNameIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFIntSubProjectTableObj.getRuntimeClassCode();
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
			throw new CFLibArgumentUnderflowException(CFIntSubProjectTableObj.class, "setRuntimeClassCode", 1, "argNewClassCode", argNewClassCode, 1);
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
		return( ICFIntTenantObj.class );
	}


	@Override
	public void minimizeMemory() {
		allSubProject = null;
		indexByTenantIdx = null;
		indexByTopProjectIdx = null;
		indexByNameIdx = null;
		List<ICFIntSubProjectObj> toForget = new LinkedList<ICFIntSubProjectObj>();
		ICFIntSubProjectObj cur = null;
		Iterator<ICFIntSubProjectObj> iter = members.values().iterator();
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
	 *	CFIntSubProjectObj.
	 */
	@Override
	public ICFIntSubProjectObj newInstance() {
		ICFIntSubProjectObj inst = new CFIntSubProjectObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntSubProjectObj.
	 */
	@Override
	public ICFIntSubProjectEditObj newEditInstance( ICFIntSubProjectObj orig ) {
		ICFIntSubProjectEditObj edit = new CFIntSubProjectEditObj( orig );
		return( edit );
	}

	@Override
	public ICFIntSubProjectObj realiseSubProject( ICFIntSubProjectObj Obj ) {
		ICFIntSubProjectObj obj = Obj;
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFIntSubProjectObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFIntSubProjectObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByTenantIdx != null ) {
				ICFIntSubProjectByTenantIdxKey keyTenantIdx =
					schema.getCFIntBackingStore().getFactorySubProject().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFIntSubProjectObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.remove( keepObj.getPKey() );
					if( mapTenantIdx.size() <= 0 ) {
						indexByTenantIdx.remove( keyTenantIdx );
					}
				}
			}

			if( indexByTopProjectIdx != null ) {
				ICFIntSubProjectByTopProjectIdxKey keyTopProjectIdx =
					schema.getCFIntBackingStore().getFactorySubProject().newByTopProjectIdxKey();
				keyTopProjectIdx.setRequiredTopProjectId( keepObj.getRequiredTopProjectId() );
				Map<CFLibDbKeyHash256, ICFIntSubProjectObj > mapTopProjectIdx = indexByTopProjectIdx.get( keyTopProjectIdx );
				if( mapTopProjectIdx != null ) {
					mapTopProjectIdx.remove( keepObj.getPKey() );
					if( mapTopProjectIdx.size() <= 0 ) {
						indexByTopProjectIdx.remove( keyTopProjectIdx );
					}
				}
			}

			if( indexByNameIdx != null ) {
				ICFIntSubProjectByNameIdxKey keyNameIdx =
					schema.getCFIntBackingStore().getFactorySubProject().newByNameIdxKey();
				keyNameIdx.setRequiredTopProjectId( keepObj.getRequiredTopProjectId() );
				keyNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByNameIdx.remove( keyNameIdx );
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByTenantIdx != null ) {
				ICFIntSubProjectByTenantIdxKey keyTenantIdx =
					schema.getCFIntBackingStore().getFactorySubProject().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFIntSubProjectObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByTopProjectIdx != null ) {
				ICFIntSubProjectByTopProjectIdxKey keyTopProjectIdx =
					schema.getCFIntBackingStore().getFactorySubProject().newByTopProjectIdxKey();
				keyTopProjectIdx.setRequiredTopProjectId( keepObj.getRequiredTopProjectId() );
				Map<CFLibDbKeyHash256, ICFIntSubProjectObj > mapTopProjectIdx = indexByTopProjectIdx.get( keyTopProjectIdx );
				if( mapTopProjectIdx != null ) {
					mapTopProjectIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByNameIdx != null ) {
				ICFIntSubProjectByNameIdxKey keyNameIdx =
					schema.getCFIntBackingStore().getFactorySubProject().newByNameIdxKey();
				keyNameIdx.setRequiredTopProjectId( keepObj.getRequiredTopProjectId() );
				keyNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByNameIdx.put( keyNameIdx, keepObj );
			}

			if( allSubProject != null ) {
				allSubProject.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allSubProject != null ) {
				allSubProject.put( keepObj.getPKey(), keepObj );
			}

			if( indexByTenantIdx != null ) {
				ICFIntSubProjectByTenantIdxKey keyTenantIdx =
					schema.getCFIntBackingStore().getFactorySubProject().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFIntSubProjectObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByTopProjectIdx != null ) {
				ICFIntSubProjectByTopProjectIdxKey keyTopProjectIdx =
					schema.getCFIntBackingStore().getFactorySubProject().newByTopProjectIdxKey();
				keyTopProjectIdx.setRequiredTopProjectId( keepObj.getRequiredTopProjectId() );
				Map<CFLibDbKeyHash256, ICFIntSubProjectObj > mapTopProjectIdx = indexByTopProjectIdx.get( keyTopProjectIdx );
				if( mapTopProjectIdx != null ) {
					mapTopProjectIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByNameIdx != null ) {
				ICFIntSubProjectByNameIdxKey keyNameIdx =
					schema.getCFIntBackingStore().getFactorySubProject().newByNameIdxKey();
				keyNameIdx.setRequiredTopProjectId( keepObj.getRequiredTopProjectId() );
				keyNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByNameIdx.put( keyNameIdx, keepObj );
			}

		}
		return( keepObj );
	}

	@Override
	public ICFIntSubProjectObj createSubProject( ICFIntSubProjectObj Obj ) {
		ICFIntSubProjectObj obj = Obj;
		ICFIntSubProject rec = obj.getSubProjectRec();
		schema.getCFIntBackingStore().getTableSubProject().createSubProject(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFIntSubProjectObj readSubProject( CFLibDbKeyHash256 pkey ) {
		return( readSubProject( pkey, false ) );
	}

	@Override
	public ICFIntSubProjectObj readSubProject( CFLibDbKeyHash256 pkey, boolean forceRead ) {
		ICFIntSubProjectObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFIntSubProject readRec = schema.getCFIntBackingStore().getTableSubProject().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getSubProjectTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFIntSubProjectObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFIntSubProjectObj readCachedSubProject( CFLibDbKeyHash256 pkey ) {
		ICFIntSubProjectObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeSubProject( ICFIntSubProjectObj obj )
	{
		final String S_ProcName = "CFIntSubProjectTableObj.reallyDeepDisposeSubProject() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFIntSubProjectObj existing = readCachedSubProject( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFIntSubProjectByTenantIdxKey keyTenantIdx = schema.getCFIntBackingStore().getFactorySubProject().newByTenantIdxKey();
		keyTenantIdx.setRequiredTenantId( existing.getRequiredTenantId() );

		ICFIntSubProjectByTopProjectIdxKey keyTopProjectIdx = schema.getCFIntBackingStore().getFactorySubProject().newByTopProjectIdxKey();
		keyTopProjectIdx.setRequiredTopProjectId( existing.getRequiredTopProjectId() );

		ICFIntSubProjectByNameIdxKey keyNameIdx = schema.getCFIntBackingStore().getFactorySubProject().newByNameIdxKey();
		keyNameIdx.setRequiredTopProjectId( existing.getRequiredTopProjectId() );
		keyNameIdx.setRequiredName( existing.getRequiredName() );


					schema.getMajorVersionTableObj().deepDisposeMajorVersionBySubProjectIdx( existing.getRequiredId() );

		if( indexByTenantIdx != null ) {
			if( indexByTenantIdx.containsKey( keyTenantIdx ) ) {
				indexByTenantIdx.get( keyTenantIdx ).remove( pkey );
				if( indexByTenantIdx.get( keyTenantIdx ).size() <= 0 ) {
					indexByTenantIdx.remove( keyTenantIdx );
				}
			}
		}

		if( indexByTopProjectIdx != null ) {
			if( indexByTopProjectIdx.containsKey( keyTopProjectIdx ) ) {
				indexByTopProjectIdx.get( keyTopProjectIdx ).remove( pkey );
				if( indexByTopProjectIdx.get( keyTopProjectIdx ).size() <= 0 ) {
					indexByTopProjectIdx.remove( keyTopProjectIdx );
				}
			}
		}

		if( indexByNameIdx != null ) {
			indexByNameIdx.remove( keyNameIdx );
		}


	}
	@Override
	public void deepDisposeSubProject( CFLibDbKeyHash256 pkey ) {
		ICFIntSubProjectObj obj = readCachedSubProject( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFIntSubProjectObj lockSubProject( CFLibDbKeyHash256 pkey ) {
		ICFIntSubProjectObj locked = null;
		ICFIntSubProject lockRec = schema.getCFIntBackingStore().getTableSubProject().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getSubProjectTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFIntSubProjectObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockSubProject", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFIntSubProjectObj> readAllSubProject() {
		return( readAllSubProject( false ) );
	}

	@Override
	public List<ICFIntSubProjectObj> readAllSubProject( boolean forceRead ) {
		final String S_ProcName = "readAllSubProject";
		if( ( allSubProject == null ) || forceRead ) {
			Map<CFLibDbKeyHash256, ICFIntSubProjectObj> map = new HashMap<CFLibDbKeyHash256,ICFIntSubProjectObj>();
			allSubProject = map;
			ICFIntSubProject[] recList = schema.getCFIntBackingStore().getTableSubProject().readAllDerived( null );
			ICFIntSubProject rec;
			ICFIntSubProjectObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntSubProjectObj realised = (ICFIntSubProjectObj)obj.realise();
			}
		}
		int len = allSubProject.size();
		ICFIntSubProjectObj arr[] = new ICFIntSubProjectObj[len];
		Iterator<ICFIntSubProjectObj> valIter = allSubProject.values().iterator();
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
		ArrayList<ICFIntSubProjectObj> arrayList = new ArrayList<ICFIntSubProjectObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntSubProjectObj> cmp = new Comparator<ICFIntSubProjectObj>() {
			@Override
			public int compare( ICFIntSubProjectObj lhs, ICFIntSubProjectObj rhs ) {
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
		List<ICFIntSubProjectObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFIntSubProjectObj> readCachedAllSubProject() {
		final String S_ProcName = "readCachedAllSubProject";
		ArrayList<ICFIntSubProjectObj> arrayList = new ArrayList<ICFIntSubProjectObj>();
		if( allSubProject != null ) {
			int len = allSubProject.size();
			ICFIntSubProjectObj arr[] = new ICFIntSubProjectObj[len];
			Iterator<ICFIntSubProjectObj> valIter = allSubProject.values().iterator();
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
		Comparator<ICFIntSubProjectObj> cmp = new Comparator<ICFIntSubProjectObj>() {
			public int compare( ICFIntSubProjectObj lhs, ICFIntSubProjectObj rhs ) {
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
	public ICFIntSubProjectObj readSubProjectByIdIdx( CFLibDbKeyHash256 Id )
	{
		return( readSubProjectByIdIdx( Id,
			false ) );
	}

	@Override
	public ICFIntSubProjectObj readSubProjectByIdIdx( CFLibDbKeyHash256 Id, boolean forceRead )
	{
		ICFIntSubProjectObj obj = readSubProject( Id, forceRead );
		return( obj );
	}

	@Override
	public List<ICFIntSubProjectObj> readSubProjectByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		return( readSubProjectByTenantIdx( TenantId,
			false ) );
	}

	@Override
	public List<ICFIntSubProjectObj> readSubProjectByTenantIdx( CFLibDbKeyHash256 TenantId,
		boolean forceRead )
	{
		final String S_ProcName = "readSubProjectByTenantIdx";
		ICFIntSubProjectByTenantIdxKey key = schema.getCFIntBackingStore().getFactorySubProject().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		Map<CFLibDbKeyHash256, ICFIntSubProjectObj> dict;
		if( indexByTenantIdx == null ) {
			indexByTenantIdx = new HashMap< ICFIntSubProjectByTenantIdxKey,
				Map< CFLibDbKeyHash256, ICFIntSubProjectObj > >();
		}
		if( ( ! forceRead ) && indexByTenantIdx.containsKey( key ) ) {
			dict = indexByTenantIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFIntSubProjectObj>();
			ICFIntSubProjectObj obj;
			ICFIntSubProject[] recList = schema.getCFIntBackingStore().getTableSubProject().readDerivedByTenantIdx( null,
				TenantId );
			ICFIntSubProject rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getSubProjectTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntSubProjectObj realised = (ICFIntSubProjectObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByTenantIdx.put( key, dict );
		}
		int len = dict.size();
		ICFIntSubProjectObj arr[] = new ICFIntSubProjectObj[len];
		Iterator<ICFIntSubProjectObj> valIter = dict.values().iterator();
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
		ArrayList<ICFIntSubProjectObj> arrayList = new ArrayList<ICFIntSubProjectObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntSubProjectObj> cmp = new Comparator<ICFIntSubProjectObj>() {
			@Override
			public int compare( ICFIntSubProjectObj lhs, ICFIntSubProjectObj rhs ) {
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
		List<ICFIntSubProjectObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFIntSubProjectObj> readSubProjectByTopProjectIdx( CFLibDbKeyHash256 TopProjectId )
	{
		return( readSubProjectByTopProjectIdx( TopProjectId,
			false ) );
	}

	@Override
	public List<ICFIntSubProjectObj> readSubProjectByTopProjectIdx( CFLibDbKeyHash256 TopProjectId,
		boolean forceRead )
	{
		final String S_ProcName = "readSubProjectByTopProjectIdx";
		ICFIntSubProjectByTopProjectIdxKey key = schema.getCFIntBackingStore().getFactorySubProject().newByTopProjectIdxKey();
		key.setRequiredTopProjectId( TopProjectId );
		Map<CFLibDbKeyHash256, ICFIntSubProjectObj> dict;
		if( indexByTopProjectIdx == null ) {
			indexByTopProjectIdx = new HashMap< ICFIntSubProjectByTopProjectIdxKey,
				Map< CFLibDbKeyHash256, ICFIntSubProjectObj > >();
		}
		if( ( ! forceRead ) && indexByTopProjectIdx.containsKey( key ) ) {
			dict = indexByTopProjectIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFIntSubProjectObj>();
			ICFIntSubProjectObj obj;
			ICFIntSubProject[] recList = schema.getCFIntBackingStore().getTableSubProject().readDerivedByTopProjectIdx( null,
				TopProjectId );
			ICFIntSubProject rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getSubProjectTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntSubProjectObj realised = (ICFIntSubProjectObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByTopProjectIdx.put( key, dict );
		}
		int len = dict.size();
		ICFIntSubProjectObj arr[] = new ICFIntSubProjectObj[len];
		Iterator<ICFIntSubProjectObj> valIter = dict.values().iterator();
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
		ArrayList<ICFIntSubProjectObj> arrayList = new ArrayList<ICFIntSubProjectObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntSubProjectObj> cmp = new Comparator<ICFIntSubProjectObj>() {
			@Override
			public int compare( ICFIntSubProjectObj lhs, ICFIntSubProjectObj rhs ) {
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
		List<ICFIntSubProjectObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFIntSubProjectObj readSubProjectByNameIdx( CFLibDbKeyHash256 TopProjectId,
		String Name )
	{
		return( readSubProjectByNameIdx( TopProjectId,
			Name,
			false ) );
	}

	@Override
	public ICFIntSubProjectObj readSubProjectByNameIdx( CFLibDbKeyHash256 TopProjectId,
		String Name, boolean forceRead )
	{
		if( indexByNameIdx == null ) {
			indexByNameIdx = new HashMap< ICFIntSubProjectByNameIdxKey,
				ICFIntSubProjectObj >();
		}
		ICFIntSubProjectByNameIdxKey key = schema.getCFIntBackingStore().getFactorySubProject().newByNameIdxKey();
		key.setRequiredTopProjectId( TopProjectId );
		key.setRequiredName( Name );
		ICFIntSubProjectObj obj = null;
		if( ( ! forceRead ) && indexByNameIdx.containsKey( key ) ) {
			obj = indexByNameIdx.get( key );
		}
		else {
			ICFIntSubProject rec = schema.getCFIntBackingStore().getTableSubProject().readDerivedByNameIdx( null,
				TopProjectId,
				Name );
			if( rec != null ) {
				obj = schema.getSubProjectTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFIntSubProjectObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFIntSubProjectObj readCachedSubProjectByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFIntSubProjectObj obj = null;
		obj = readCachedSubProject( Id );
		return( obj );
	}

	@Override
	public List<ICFIntSubProjectObj> readCachedSubProjectByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		final String S_ProcName = "readCachedSubProjectByTenantIdx";
		ICFIntSubProjectByTenantIdxKey key = schema.getCFIntBackingStore().getFactorySubProject().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		ArrayList<ICFIntSubProjectObj> arrayList = new ArrayList<ICFIntSubProjectObj>();
		if( indexByTenantIdx != null ) {
			Map<CFLibDbKeyHash256, ICFIntSubProjectObj> dict;
			if( indexByTenantIdx.containsKey( key ) ) {
				dict = indexByTenantIdx.get( key );
				int len = dict.size();
				ICFIntSubProjectObj arr[] = new ICFIntSubProjectObj[len];
				Iterator<ICFIntSubProjectObj> valIter = dict.values().iterator();
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
			ICFIntSubProjectObj obj;
			Iterator<ICFIntSubProjectObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFIntSubProjectObj> cmp = new Comparator<ICFIntSubProjectObj>() {
			@Override
			public int compare( ICFIntSubProjectObj lhs, ICFIntSubProjectObj rhs ) {
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
	public List<ICFIntSubProjectObj> readCachedSubProjectByTopProjectIdx( CFLibDbKeyHash256 TopProjectId )
	{
		final String S_ProcName = "readCachedSubProjectByTopProjectIdx";
		ICFIntSubProjectByTopProjectIdxKey key = schema.getCFIntBackingStore().getFactorySubProject().newByTopProjectIdxKey();
		key.setRequiredTopProjectId( TopProjectId );
		ArrayList<ICFIntSubProjectObj> arrayList = new ArrayList<ICFIntSubProjectObj>();
		if( indexByTopProjectIdx != null ) {
			Map<CFLibDbKeyHash256, ICFIntSubProjectObj> dict;
			if( indexByTopProjectIdx.containsKey( key ) ) {
				dict = indexByTopProjectIdx.get( key );
				int len = dict.size();
				ICFIntSubProjectObj arr[] = new ICFIntSubProjectObj[len];
				Iterator<ICFIntSubProjectObj> valIter = dict.values().iterator();
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
			ICFIntSubProjectObj obj;
			Iterator<ICFIntSubProjectObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFIntSubProjectObj> cmp = new Comparator<ICFIntSubProjectObj>() {
			@Override
			public int compare( ICFIntSubProjectObj lhs, ICFIntSubProjectObj rhs ) {
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
	public ICFIntSubProjectObj readCachedSubProjectByNameIdx( CFLibDbKeyHash256 TopProjectId,
		String Name )
	{
		ICFIntSubProjectObj obj = null;
		ICFIntSubProjectByNameIdxKey key = schema.getCFIntBackingStore().getFactorySubProject().newByNameIdxKey();
		key.setRequiredTopProjectId( TopProjectId );
		key.setRequiredName( Name );
		if( indexByNameIdx != null ) {
			if( indexByNameIdx.containsKey( key ) ) {
				obj = indexByNameIdx.get( key );
			}
			else {
				Iterator<ICFIntSubProjectObj> valIter = members.values().iterator();
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
			Iterator<ICFIntSubProjectObj> valIter = members.values().iterator();
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
	public void deepDisposeSubProjectByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFIntSubProjectObj obj = readCachedSubProjectByIdIdx( Id );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeSubProjectByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		final String S_ProcName = "deepDisposeSubProjectByTenantIdx";
		ICFIntSubProjectObj obj;
		List<ICFIntSubProjectObj> arrayList = readCachedSubProjectByTenantIdx( TenantId );
		if( arrayList != null )  {
			Iterator<ICFIntSubProjectObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeSubProjectByTopProjectIdx( CFLibDbKeyHash256 TopProjectId )
	{
		final String S_ProcName = "deepDisposeSubProjectByTopProjectIdx";
		ICFIntSubProjectObj obj;
		List<ICFIntSubProjectObj> arrayList = readCachedSubProjectByTopProjectIdx( TopProjectId );
		if( arrayList != null )  {
			Iterator<ICFIntSubProjectObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeSubProjectByNameIdx( CFLibDbKeyHash256 TopProjectId,
		String Name )
	{
		ICFIntSubProjectObj obj = readCachedSubProjectByNameIdx( TopProjectId,
				Name );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFIntSubProjectObj updateSubProject( ICFIntSubProjectObj Obj ) {
		ICFIntSubProjectObj obj = Obj;
		schema.getCFIntBackingStore().getTableSubProject().updateSubProject( null,
			Obj.getSubProjectRec() );
		obj = (ICFIntSubProjectObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteSubProject( ICFIntSubProjectObj Obj ) {
		ICFIntSubProjectObj obj = Obj;
		schema.getCFIntBackingStore().getTableSubProject().deleteSubProject( null,
			obj.getSubProjectRec() );
		Obj.forget();
	}

	@Override
	public void deleteSubProjectByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFIntSubProjectObj obj = readSubProject(Id);
		if( obj != null ) {
			ICFIntSubProjectEditObj editObj = (ICFIntSubProjectEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFIntSubProjectEditObj)obj.beginEdit();
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
		deepDisposeSubProjectByIdIdx( Id );
	}

	@Override
	public void deleteSubProjectByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		ICFIntSubProjectByTenantIdxKey key = schema.getCFIntBackingStore().getFactorySubProject().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		if( indexByTenantIdx == null ) {
			indexByTenantIdx = new HashMap< ICFIntSubProjectByTenantIdxKey,
				Map< CFLibDbKeyHash256, ICFIntSubProjectObj > >();
		}
		if( indexByTenantIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFIntSubProjectObj> dict = indexByTenantIdx.get( key );
			schema.getCFIntBackingStore().getTableSubProject().deleteSubProjectByTenantIdx( null,
				TenantId );
			Iterator<ICFIntSubProjectObj> iter = dict.values().iterator();
			ICFIntSubProjectObj obj;
			List<ICFIntSubProjectObj> toForget = new LinkedList<ICFIntSubProjectObj>();
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
			schema.getCFIntBackingStore().getTableSubProject().deleteSubProjectByTenantIdx( null,
				TenantId );
		}
		deepDisposeSubProjectByTenantIdx( TenantId );
	}

	@Override
	public void deleteSubProjectByTopProjectIdx( CFLibDbKeyHash256 TopProjectId )
	{
		ICFIntSubProjectByTopProjectIdxKey key = schema.getCFIntBackingStore().getFactorySubProject().newByTopProjectIdxKey();
		key.setRequiredTopProjectId( TopProjectId );
		if( indexByTopProjectIdx == null ) {
			indexByTopProjectIdx = new HashMap< ICFIntSubProjectByTopProjectIdxKey,
				Map< CFLibDbKeyHash256, ICFIntSubProjectObj > >();
		}
		if( indexByTopProjectIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFIntSubProjectObj> dict = indexByTopProjectIdx.get( key );
			schema.getCFIntBackingStore().getTableSubProject().deleteSubProjectByTopProjectIdx( null,
				TopProjectId );
			Iterator<ICFIntSubProjectObj> iter = dict.values().iterator();
			ICFIntSubProjectObj obj;
			List<ICFIntSubProjectObj> toForget = new LinkedList<ICFIntSubProjectObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByTopProjectIdx.remove( key );
		}
		else {
			schema.getCFIntBackingStore().getTableSubProject().deleteSubProjectByTopProjectIdx( null,
				TopProjectId );
		}
		deepDisposeSubProjectByTopProjectIdx( TopProjectId );
	}

	@Override
	public void deleteSubProjectByNameIdx( CFLibDbKeyHash256 TopProjectId,
		String Name )
	{
		if( indexByNameIdx == null ) {
			indexByNameIdx = new HashMap< ICFIntSubProjectByNameIdxKey,
				ICFIntSubProjectObj >();
		}
		ICFIntSubProjectByNameIdxKey key = schema.getCFIntBackingStore().getFactorySubProject().newByNameIdxKey();
		key.setRequiredTopProjectId( TopProjectId );
		key.setRequiredName( Name );
		ICFIntSubProjectObj obj = null;
		if( indexByNameIdx.containsKey( key ) ) {
			obj = indexByNameIdx.get( key );
			schema.getCFIntBackingStore().getTableSubProject().deleteSubProjectByNameIdx( null,
				TopProjectId,
				Name );
			obj.forget();
		}
		else {
			schema.getCFIntBackingStore().getTableSubProject().deleteSubProjectByNameIdx( null,
				TopProjectId,
				Name );
		}
		deepDisposeSubProjectByNameIdx( TopProjectId,
				Name );
	}
}