signature JSON_CONTROLLER_REGISTRY =
sig
  (*
    dispatch takes:
       ((controller, module, function), JSON input)
    and returns:
       (status, JSON output)
  *)
  val dispatch : (string * string * string) * Json.json -> Json.json
end

structure JsonControllerRegistry : JSON_CONTROLLER_REGISTRY =
struct

open JsonControllerUtil

structure RG_Controller = JsonController(RG_Theory)

val ctab = Symtab.make [
 (RG_Controller.Theory.theory_name, RG_Controller.dispatch)
]

fun dispatch ((controller, module, function), json_input) =
  case Symtab.lookup ctab controller
    of SOME cdisp => cdisp ((module, function), json_input)
     | NONE => raise user_exn ("Controller not found: " ^ controller)

    
end