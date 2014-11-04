package model

import model.CustomPlaySalatContext
import CustomPlaySalatContext._
import com.novus.salat.annotations._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.TypeImports.ObjectId
import com.novus.salat.dao.{SalatDAO, ModelCompanion}
import model.users._


case class Department(@Key("_id") id: ObjectId = new ObjectId,
                      name: String,
                      description: String)

object Department extends ModelCompanion[Department, ObjectId] {

  val collection = MongoConnection()("dnu")("department")
  val dao = new SalatDAO[Department, ObjectId](collection = collection) {}

  def find(id: String) = dao.findOneById(new ObjectId(id))

  def create(name: String, description: String): Option[ObjectId] = {
    dao.insert(Department(name = name, description = description))
  }

  def delete(id: String) {
    dao.remove(MongoDBObject("_id" -> new ObjectId(id)))
  }

  def getGroups(id: String): List[Group] = {
    Group.find(MongoDBObject("departmentId" -> new ObjectId(id))).toList
  }

  def getManagers(id: String): List[DepartmentManager] = {
    User.find(MongoDBObject("departmentId" -> new ObjectId(id))).toList.asInstanceOf[List[DepartmentManager]]
  }

  def getTeachers(){}

}
