package baseAPI.API.Sistema.Service;

import baseAPI.API.Sistema.DTO.PacoteDTO;
import baseAPI.API.Sistema.Enum.Acao;
import baseAPI.API.Sistema.Model.Arquivos;
import baseAPI.API.Sistema.Model.Backup;
import baseAPI.API.Sistema.Model.Pacote;
import baseAPI.API.Sistema.Repository.ArquivosRepository;
import baseAPI.API.Sistema.Repository.BackupRepository;
import baseAPI.API.Sistema.Repository.PacoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipOutputStream;


import static java.nio.file.Paths.get;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.ok;

@Service
public class PacoteService {

    @Autowired
    PacoteRepository pacoteRepository;

    @Autowired
    BackupRepository backupRepository;

    @Autowired
    ArquivosRepository arquivosRepository;

    static final int TAMANHO_BUFFER = 4096; // 4kb
    private static String caminhoImagem = "D:\\PROJETOS JAVA\\PROJETOS\\api_Java_Receitas\\Upload\\Pacotes\\";

    private static String caminhoImagemzip = "D:\\PROJETOS JAVA\\PROJETOS\\api_Java_Receitas\\Upload\\Download\\";

    private static String caminhoImagembackup = "D:\\PROJETOS JAVA\\PROJETOS\\api_Java_Receitas\\Upload\\Backup\\";

    public ResponseEntity<List<Pacote>> listar()
    {
        try{
            return new ResponseEntity<>(pacoteRepository.findAll(), OK);
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getStackTrace();
        }
        return null;
    }

    public ResponseEntity<Pacote> buscarPorId(Long id)
    {
        try{
            if(pacoteRepository.existsById(id))
            {
                Optional<Pacote> entidade = pacoteRepository.findById(id);
                return new ResponseEntity<Pacote>(entidade.get(), ACCEPTED);
            }else {
                ResponseEntity responseEntity = new ResponseEntity<Pacote>(BAD_GATEWAY);
                return responseEntity;
            }
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getStackTrace();
        }
        return null;
    }

    public ResponseEntity<PacoteDTO> salvar(PacoteDTO pacoteDTO, MultipartFile[] files)throws IOException
    {
        try{
            if(pacoteDTO != null)
                {
                    Pacote pacote = new Pacote();
                    Arquivos arquivos = new Arquivos();
                    List<String> lista = new ArrayList<>();
                    pacote.setNome(pacoteDTO.getNome());
                    pacote.setDescrisao(pacoteDTO.getDescrisao());
                    int dig = (int) (101 + Math.random() * 899);
                    String codigo = "dw_"+dig+"_"+pacoteDTO.getNome();
                    pacote.setCodigoDownload(codigo);
                    boolean pasta = new File(caminhoImagem + "\\"+codigo).mkdir();
                    for(MultipartFile file : files)
                    {
                        byte[] bytes = file.getBytes();
                        Path caminho = get(caminhoImagem + codigo+"\\"+file.getOriginalFilename());
                        Files.write(caminho, bytes);
                        lista.add(file.getOriginalFilename());
                    }
                    arquivos.setArquivos(lista);
                    arquivosRepository.save(arquivos);
                    pacote.setArquivos(arquivos);
                    pacote.setArquivoDownload(codigo+"_"+pacoteDTO.getNome()+".zip");
                    pacote.setDataCriacao(LocalDateTime.now());
                    pacoteRepository.save(pacote);
                    Backup backup = new Backup();
                    backup.setAcao(Acao.PACOTE_CRIADO);
                    backup.setPacote(pacote);
                    backup.setDataAcao(LocalDateTime.now());
                    backupRepository.save(backup);
                    ziparArquivos(pacoteDTO.getNome(),codigo);
                    return new ResponseEntity<>(CREATED);
                }
            else
            {
                return new ResponseEntity<>(BAD_REQUEST);
            }
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getStackTrace();
        }
        return null;
    }

    public ResponseEntity<PacoteDTO> ziparArquivos(String nomePacote,String codigo)throws IOException
    {
        try {
            Pacote pacote = pacoteRepository.findBycodigoDownload(codigo);
            String sourceFile = caminhoImagem + codigo + "\\";
            int dig = (int) (100+ Math.random() * 899);
            FileOutputStream fos = new FileOutputStream(caminhoImagemzip + dig+"_"+nomePacote+".zip");
            pacote.setArquivoDownload( dig+"_"+nomePacote+".zip");
            pacoteRepository.save(pacote);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            File fileToZip = new File(sourceFile);
            String fileName = fileToZip.getName();
            UtilService.zipFile(fileToZip, fileToZip.getName(), zipOut);
            zipOut.close();
            fos.close();
            return new ResponseEntity<>(OK);
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getMessage();
        }
        return null;
    }

    public ResponseEntity<PacoteDTO> ziparArquivosBk(Long id)throws IOException
    {
        try {
            Pacote pacote = pacoteRepository.findById(id).get();
            String sourceFile = caminhoImagem + pacote.getCodigoDownload() + "\\";
            FileOutputStream fos = new FileOutputStream(caminhoImagembackup +pacote.getArquivoDownload()+".zip");
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            File fileToZip = new File(sourceFile);
            String fileName = fileToZip.getName();
            UtilService.zipFile(fileToZip, fileToZip.getName(), zipOut);
            zipOut.close();
            fos.close();
            return new ResponseEntity<>(OK);
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getMessage();
        }
        return null;
    }

    public ResponseEntity<Resource> downloadFiles(String codigo) throws IOException
    {
        if(codigo != null)
        {
           if(codigo != null)
           {
               Pacote pacote = pacoteRepository.findBycodigoDownload(codigo);
               if(pacote != null)
               {
                   String filename = pacote.getArquivoDownload();
                   Path filePath = get(caminhoImagemzip).toAbsolutePath().normalize().resolve(filename);
                   if (!Files.exists(filePath)) {
                       throw new FileNotFoundException(filename + " was not found on the server");
                   }
                   Backup backup = new Backup();
                   backup.setAcao(Acao.PACOTE_DOWNLOAD);
                   backup.setPacote(pacote);
                   backup.setDataAcao(LocalDateTime.now());
                   backupRepository.save(backup);
                   Resource resource = new UrlResource(filePath.toUri());
                   HttpHeaders httpHeaders = new HttpHeaders();
                   httpHeaders.add("File-Name", filename);
                   httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
                   return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                           .headers(httpHeaders).body(resource);
           }

            }
        }
        return new ResponseEntity<>(BAD_REQUEST);
    }
    public ResponseEntity<PacoteDTO> AlterarArquivos(Long idpacote, MultipartFile[] files) throws IOException
    {
        try{
            if(pacoteRepository.existsById(idpacote))
            {
                Pacote pacote = pacoteRepository.findById(idpacote).get();
                if(pacote != null)
                {
                Arquivos arquivos = arquivosRepository.findById(pacote.getArquivos().getId()).get();
                List<String> lista = new ArrayList<>();
                Backup backup = new Backup();

                    for(MultipartFile file : files)
                    {
                        byte[] bytes = file.getBytes();
                        Path caminho = get(caminhoImagem + pacote.getCodigoDownload()+"\\"+file.getOriginalFilename());
                        Files.write(caminho, bytes);
                        lista.add(file.getOriginalFilename());
                    }

                    pacote.getArquivos().getArquivos().forEach(item ->
                    {
                        removeArquivo(caminhoImagem+pacote.getCodigoDownload()+"\\"+item);
                    });
                    arquivos.setArquivos(lista);
                    arquivosRepository.save(arquivos);
                    pacote.setDataEdicao(LocalDateTime.now());
                    pacoteRepository.save(pacote);
                    backup.setAcao(Acao.PACOTE_EDITADO);
                    backup.setPacote(pacote);
                    backup.setArquivoDeletado(pacote.getArquivoDownload());
                    backup.setDataAcao(LocalDateTime.now());
                    backupRepository.save(backup);
                    removeArquivo(caminhoImagemzip+pacote.getArquivoDownload());
                    ziparArquivos(pacote.getNome(), pacote.getCodigoDownload());
                    ziparArquivosBk(idpacote);
                    return new ResponseEntity<>(OK);
                }

            }
            else
            {
                return new ResponseEntity<>(BAD_REQUEST);
            }
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getStackTrace();
        }
        return null;
    }

    public ResponseEntity<PacoteDTO> AdicionarArquivos(Long idpacote, MultipartFile[] files) throws IOException
    {
        try{
            if(pacoteRepository.existsById(idpacote))
            {
                Pacote pacote = pacoteRepository.findById(idpacote).get();
                if(pacote != null)
                {
                Arquivos arquivos = arquivosRepository.findById(pacote.getArquivos().getId()).get();
                List<String> lista = new ArrayList<>();
                Backup backup = new Backup();

                    for(MultipartFile file : files)
                    {
                        byte[] bytes = file.getBytes();
                        Path caminho = get(caminhoImagem + pacote.getCodigoDownload()+"\\"+file.getOriginalFilename());
                        Files.write(caminho, bytes);
                        lista.add(file.getOriginalFilename());
                    }
                    arquivos.getArquivos().addAll(lista);
                    arquivosRepository.save(arquivos);
                    pacote.setDataEdicao(LocalDateTime.now());
                    pacoteRepository.save(pacote);
                    backup.setAcao(Acao.PACOTE_EDITADO);
                    backup.setPacote(pacote);
                    backup.setArquivoDeletado(pacote.getArquivoDownload());
                    backup.setDataAcao(LocalDateTime.now());
                    backupRepository.save(backup);
                    removeArquivo(caminhoImagemzip+pacote.getArquivoDownload());
                    ziparArquivos(pacote.getNome(), pacote.getCodigoDownload());
                    ziparArquivosBk(idpacote);
                    return new ResponseEntity<>(OK);
                }

            }
            else
            {
                return new ResponseEntity<>(BAD_REQUEST);
            }
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getStackTrace();
        }
        return null;
    }

    public void removeArquivo (String fileName)
    {
        File file = new File(fileName);
        file.delete();
    }

    public ResponseEntity<PacoteDTO> deletar(Long id)
    {
        try{
            if(pacoteRepository.existsById(id))
            {
                Pacote pacote = pacoteRepository.findById(id).get();
                pacoteRepository.deleteById(id);
                ResponseEntity<PacoteDTO> tResponseEntity = new ResponseEntity<>(OK);
                return tResponseEntity;

            }else {
                return new ResponseEntity<>(BAD_REQUEST);
            }
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getStackTrace();
        }
        return null;
    }







}
