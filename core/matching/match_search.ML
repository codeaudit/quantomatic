(* The matching interface *)
(* see docs/matching_algo for details *)
signature MATCH_SEARCH_SHARING =
sig
  structure Graph : OGRAPH_SHARING
  structure Match : OGRAPH_MATCH_SHARING
  sharing Graph = Match.Graph
end

signature MATCH_SEARCH =
sig
  structure Log : LOG;


  structure Match : OGRAPH_MATCH
  structure Graph : OGRAPH
  structure Sharing : MATCH_SEARCH_SHARING
    sharing Sharing.Graph = Graph.Sharing
    sharing Sharing.Match = Match.Sharing
  
  
  val match : Graph.T      (* pattern graph *)
    -> Graph.T             (* target graph *)
    -> Match.T Seq.seq (* (lazy) list of matches *)

  val match_subgraph : Graph.T  (* pattern graph *)
    -> Graph.T                  (* target graph *)
    -> V.NSet.T             (* target subgraph verts *)
    -> Match.T Seq.seq      (* (lazy) list of matches *)
  
  (* these versions are for efficiency if you can guarantee
   * that the graphs are already normalised
   *)

  val match_normalised : Graph.T (* pattern graph (normalised) *)
    -> Graph.T                   (* target graph (normalised) *)
    -> Match.T Seq.seq       (* (lazy) list of matches *)
  
  val match_subgraph_normalised : Graph.T (* pattern graph (normalised) *)
    -> Graph.T                            (* target graph (normalised) *)
    -> V.NSet.T                       (* target subgraph verts *)
    -> Match.T Seq.seq                (* (lazy) list of matches *)
end

signature BG_MATCH_SEARCH_SHARING =
sig
  structure Graph : OGRAPH_SHARING
  structure Match : BANG_GRAPH_MATCH_SHARING
  sharing Graph = Match.Graph
end

signature BG_MATCH_SEARCH =
sig

  structure Log : LOG;
  structure InnerLog : LOG;

  structure Graph : BANG_GRAPH;
  structure Match: BANG_GRAPH_MATCH;
  structure Sharing : MATCH_SEARCH_SHARING
    sharing Sharing.Graph = Graph.Sharing
    sharing Sharing.Match = Match.Sharing
  
  (* Searches for matches *)
  val match
     : Graph.T              (* pattern graph *)
    -> Graph.T              (* target graph *)
    -> Match.T Seq.seq      (* (lazy) list of matches *)

  (* Searches for matches that complete the given node map. If the
     given node map is already invalid, returns no matches. *)
  val match_with_initial_nodes
     : Graph.T              (* pattern graph *)
    -> Graph.T              (* target graph *)
    -> VVInj.T              (* an initial node map *)
    -> Match.T Seq.seq      (* (lazy) list of matches *)
  
  (* Searches for matches *)
  val match_subgraph
     : Graph.T              (* pattern graph *)
    -> Graph.T              (* target graph *)
    -> V.NSet.T             (* target subgraph verts *)
    -> Match.T Seq.seq      (* (lazy) list of matches *)
  
  (* Searches for matches *)
  val match_normalised
     : Graph.T              (* pattern graph *)
    -> Graph.T              (* target graph (normalised) *)
    -> Match.T Seq.seq      (* (lazy) list of matches *)
  
  (* Searches for matches *)
  val match_subgraph_normalised
     : Graph.T              (* pattern graph *)
    -> Graph.T              (* target graph (normalised) *)
    -> V.NSet.T             (* target subgraph verts *)
    -> Match.T Seq.seq      (* (lazy) list of matches *)
end
