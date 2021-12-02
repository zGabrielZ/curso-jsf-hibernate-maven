package br.com.gabrielfereira.manager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import br.com.gabrielfereira.entidade.Cidade;
import br.com.gabrielfereira.entidade.Endereco;
import br.com.gabrielfereira.entidade.Estado;
import br.com.gabrielfereira.entidade.Framework;
import br.com.gabrielfereira.entidade.Linguagem;
import br.com.gabrielfereira.entidade.NivelProgramador;
import br.com.gabrielfereira.entidade.Perfil;
import br.com.gabrielfereira.entidade.Pessoa;
import br.com.gabrielfereira.entidade.enums.Sexo;
import br.com.gabrielfereira.exception.RegraDeNegocioException;
import br.com.gabrielfereira.service.CidadeService;
import br.com.gabrielfereira.service.EstadoService;
import br.com.gabrielfereira.service.FrameworkService;
import br.com.gabrielfereira.service.LinguagemService;
import br.com.gabrielfereira.service.NivelProgramadorService;
import br.com.gabrielfereira.service.PerfilService;
import br.com.gabrielfereira.service.PessoaService;
import br.com.gabrielfereira.util.JSFUtil;
import net.bootsfaces.component.selectOneMenu.SelectOneMenu;

@Named(value = "pessoaManager")
@ViewScoped
public class PessoaManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PessoaService pessoaService;
	
	@Inject
	private EstadoService estadoService;
	
	@Inject
	private CidadeService cidadeService;
	
	@Inject
	private FrameworkService frameworkService;
	
	@Inject
	private PerfilService perfilService;
	
	@Inject
	private NivelProgramadorService nivelProgramadorService;
	
	@Inject
	private LinguagemService linguagemService;
	
	private Pessoa pessoa;
	
	private Endereco endereco;
	
	private Cidade cidade;
	
	private Estado estado;
	
	private List<Framework> framework;
	
	private List<Linguagem> linguagems;
	
	private Perfil perfil;
	
	private NivelProgramador nivelProgramador;
	
	private List<Pessoa> pessoas;
	
	private Pessoa pessoaSelecionado;
	
	private List<SelectItem> cidadesPorEstados;
	
	private Part arquivoFoto;
	
	@PostConstruct
	public void iniciar() {
		pessoa = new Pessoa();
		endereco = new Endereco();
		cidade = new Cidade();
		estado = new Estado();
		framework = new ArrayList<Framework>();
		perfil = new Perfil();
		nivelProgramador = new NivelProgramador();
		linguagems = new ArrayList<>();
		pessoa.setEndereco(endereco);
		pessoa.setPerfil(perfil);
		pessoa.getFrameworks().addAll(framework);
		pessoa.getLinguagens().addAll(linguagems);
		pessoa.setNivelProgramador(nivelProgramador);
		endereco.setCidade(cidade);
		cidade.setEstado(estado);
		estado.getCidades().add(cidade);
		pessoas = pessoaService.listagemLimit10();
		verificarParametro();
	}
	
	private void verificarParametro() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id = params.get("codigoPessoa");
		if(id != null) {
			pessoa.setId(Integer.parseInt(id));
			pessoa = pessoaService.pesquisarPorId(pessoa);
			pessoa.setFrameworks(frameworkService.findFrameworkByIdPessoa(pessoa.getId()));
			pessoa.setLinguagens(linguagemService.findLinguagemByIdPessoa(pessoa.getId()));
			setCidadesPorEstados(cidadeService.getCidadesPorEstado(pessoa.getEndereco().getCidade().getEstado().getId()));
		}
	}
	
	public void novo() {
		pessoa = new Pessoa();
		endereco = new Endereco();
		estado = new Estado();
		cidade = new Cidade();
		framework = new ArrayList<>();
		perfil = new Perfil();
		nivelProgramador = new NivelProgramador();
		linguagems = new ArrayList<>();
		pessoa.getLinguagens().addAll(linguagems);
		pessoa.setEndereco(endereco);
		pessoa.setPerfil(perfil);
		pessoa.setNivelProgramador(nivelProgramador);
		pessoa.getFrameworks().addAll(framework);
		endereco.setCidade(cidade);
		cidade.setEstado(estado);
		estado.getCidades().add(cidade);
	}
	
	public void cadastro() {
		if(pessoa.getId() == null) {
			inserir();
		} else {
			atualizar();
		}
	}
	
	public void inserir() {
		try {
			processarImagem();
			pessoaService.inserir(pessoa);
			JSFUtil.enviarMensagemSucesso("Inserido com sucesso !", "frmPessoa:msg");
			pessoa = new Pessoa();
		} catch (RegraDeNegocioException | IOException e) {
			FacesContext.getCurrentInstance().addMessage("frmPessoa:msg", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), null));
		}
	}
	
	public void processarImagem() throws IOException {
		if(arquivoFoto != null) {
			if(arquivoFoto.getContentType().equals("image/jpeg") || arquivoFoto.getContentType().equals("image/png")
					|| arquivoFoto.getContentType().equals("image/jpg")) {
				
				String caminhoImagem = caminhoImagem(arquivoFoto);
				String extensao = extensaoImagem(arquivoFoto);
				pessoa.setCaminhoImagem(caminhoImagem);
				pessoa.setExtensao(extensao);
				
				// Enviar a imagem processada para o caminho da pasta
				
				// Cria um espaço de memória que vai armazenar o conteúdo da imagem 
				byte[] bytesImagem = new byte[(int) arquivoFoto.getSize()];
				
				// Le o conteudo da imagem e joga dentro do array de bytes
				arquivoFoto.getInputStream().read(bytesImagem);
				
				// Cria uma referencia para o arquivo que será criado no lado do servidor
				File arquivo = new File(caminhoImagem);
				
				// Cria o objeto que irá manipular o arquivo criado
				FileOutputStream fileOutputStream = new FileOutputStream(arquivo);
				
				// Escreve o conteudo da imagem (upload) dentro do arquivo servidor
				fileOutputStream.write(bytesImagem);
				
				fileOutputStream.close();
				
			}
		}
	}
	
	private String caminhoImagem(Part arquivoFoto) {
		String caminho = "C:\\Users\\Acer\\Desktop\\Curso Java\\Modulo 24 - JSF 2.2 e JPA Hibernate\\projeto-jsf\\src\\main\\webapp\\resources\\imagem-pessoa";
		String nomeArquivo = arquivoFoto.getSubmittedFileName();
		return caminho + "\\" +nomeArquivo;
	}
	
	private String extensaoImagem(Part arquivoFoto) {
		String extensao = arquivoFoto.getContentType().split("\\/")[1];
		return extensao;
	}

	public void atualizar() {
		try {
			processarImagem();
			pessoaService.atualizar(pessoa);
			JSFUtil.enviarMensagemSucesso("Atualizado com sucesso !", "frmPessoa:msg");
			pessoa = new Pessoa();
		} catch (RegraDeNegocioException | IOException e) {
			FacesContext.getCurrentInstance().addMessage("frmPessoa:msg", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), null));
		}
	}
	
	public void deletar() {
		pessoaService.remover(pessoaSelecionado);
		JSFUtil.enviarMensagemSucesso("Removido com sucesso !", "frmDataTablePessoa:msg");
		iniciar();
	}
	
	public void consultarCep() {
		try {
			Endereco endereco = pessoaService.pesquisarCep(pessoa.getEndereco().getCep());
			Cidade cidade = new Cidade();
			Estado estado = new Estado();
			cidade.setEstado(estado);
			estado.getCidades().add(cidade);
			endereco.setCidade(cidade);
			pessoa.setEndereco(endereco);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), null));
		}
	}
	
	public void buscarCidadesPorEstado(AjaxBehaviorEvent event) {
		// Com JSF SERIA HtmlSelectOneMenu, mas com bootfaces é SelectOneMenu
		Estado estado = (Estado)((SelectOneMenu) event.getSource()).getValue();
		if(estado != null) {
			setCidadesPorEstados(cidadeService.getCidadesPorEstado(estado.getId()));
		}
	}
	
	public void downloadImage() throws IOException {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String idPessoaImagem = params.get("downloadArquivo");
		Pessoa pessoaPesquisar = new Pessoa();
		pessoaPesquisar.setId(Integer.parseInt(idPessoaImagem));
		Pessoa pessoa = pessoaService.pesquisarPorId(pessoaPesquisar);
		
		if(pessoa.getCaminhoImagem() != null) {
			
			File arquivo = new File(pessoa.getCaminhoImagem());
			FileInputStream fileInputStream = new FileInputStream(arquivo);
			byte[] arrayImagem = new byte[(int) arquivo.length()];
			fileInputStream.read(arrayImagem);
			fileInputStream.close();
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			
			response.setHeader("Content-disposition","attachment; filename=download."+pessoa.getExtensao());
			response.setContentType("application/octet-stream");  // Midia arquivo, imagem, fotos, videos
			response.setContentLength(arrayImagem.length);
			
			response.getOutputStream().write(arrayImagem);
			response.getOutputStream().flush();
			FacesContext.getCurrentInstance().responseComplete();
		
		}  else {
			FacesContext.getCurrentInstance().addMessage("frmDataTablePessoa:msg", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Não é possível fazer o download da imagem", null));
		}
		
	}
	
	public void registrarLog() {
		// Criar rotina gravação de logs por exemplo
		System.out.println("Exemplo de criação de rotina");
	}
	
	public void mudancaNome(ValueChangeEvent event) {
		// Se usar o do html, seria onchange, e o do jsf seria valueChangeListener, porem so é capturado quando submeter o formulario
		System.out.println("Valor antigo do nome : " + event.getOldValue());
		System.out.println("Valor novo  do nome: " + event.getNewValue());
	}
	
	public void mudancaSobrenome(ValueChangeEvent event) {
		// Se usar o do html, seria onchange, e o do jsf seria valueChangeListener, porem so é capturado quando submeter o formulario
		System.out.println("Valor antigo do sobrenome : " + event.getOldValue());
		System.out.println("Valor novo do sobrenome: " + event.getNewValue());
	}
	
	public Sexo[] getSexos() {
		return Sexo.values();
	}
	
	public List<SelectItem> getLinguagemsSelect(){
		return linguagemService.getLinguagems();
	}
	
	public List<SelectItem> getNivelProgramador(){
		return nivelProgramadorService.getNivelProgramadors();
	}
	
	public List<SelectItem> getPerfis(){
		return perfilService.getPerfils();
	}
	
	public List<SelectItem> getFrameworksSelect(){
		return frameworkService.getFrameworks();
	}
	
	public List<SelectItem> getEstados(){
		return estadoService.getEstados();
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	
	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Pessoa getPessoaSelecionado() {
		return pessoaSelecionado;
	}

	public void setPessoaSelecionado(Pessoa pessoaSelecionado) {
		this.pessoaSelecionado = pessoaSelecionado;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<SelectItem> getCidadesPorEstados() {
		return cidadesPorEstados;
	}

	public void setCidadesPorEstados(List<SelectItem> cidadesPorEstados) {
		this.cidadesPorEstados = cidadesPorEstados;
	}

	public Part getArquivoFoto() {
		return arquivoFoto;
	}

	public void setArquivoFoto(Part arquivoFoto) {
		this.arquivoFoto = arquivoFoto;
	}	

}
